package controller.analyzer;

import _options.Options;
import controller.types.ctl.Label;
import controller.types.ctl.Relation;
import controller.types.graph.*;
import controller.utils.ExceptionMessage;
import controller.utils.ListHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static controller.utils.Utils.getLineNumber;

public class ReadXml {

    VertexList vertexList;
    Options options;
    ListHelper listHelper;
    Document xmlDoc;

    public VertexList convertXmlToVertexList(String[] selectedXmlFilenamesArr, LabelHash labelHash) throws IOException, SAXException, ParserConfigurationException, ExceptionMessage {

        ReadXml xml;
        Options options;
        ArrayList<String> xmlFilenamesArrList;

        VertexList vertexList;
        String xmlDirPath;

        xml = new ReadXml();
        options = new Options();

        xmlDirPath = options.getPathToXmlDir();

        xmlFilenamesArrList = new ArrayList<String>();

        for (int i=0; i<selectedXmlFilenamesArr.length; i++) {
            String fileName = selectedXmlFilenamesArr[i];
            xmlFilenamesArrList.add(fileName);
        }

        ArrayList<VertexList> individualVertexLists = new ArrayList<VertexList>();
        for (String xmlFilename : xmlFilenamesArrList) {
            VertexList thisVertexList = xml.xmlFilepathToVertexList(xmlFilename, xmlDirPath, labelHash);
            individualVertexLists.add(thisVertexList);
        }

        if (individualVertexLists.size() > 1) {
            VertexList combinedVertexList = new VertexList();

            int vertexNum = 0;
            // Vertex newRoot = new Vertex();
            Vertex newRoot = new Vertex(0,"s0",VertexKind.PARALLEL,null,"par 0",null,null,null,null,0,0,0,0,null,null,0,0,true,true);
            newRoot.setNumber(vertexNum);
            newRoot.setIsRoot(true);
            combinedVertexList.addVertex(newRoot);
            combinedVertexList.setRoot(newRoot);
            vertexNum++;

            for (VertexList individualVertexList : individualVertexLists) {
                Vertex thisRoot = individualVertexList.getRoot();
                newRoot.linkChildAndRelation(thisRoot);
                thisRoot.setIsRoot(false);

                for (Vertex vertex : individualVertexList.getList()) {
                    vertex.setNumber(vertexNum);
                    combinedVertexList.addVertex(vertex);
                    vertexNum++;
                }

            }

            vertexList = combinedVertexList;
        } else {
            vertexList = individualVertexLists.get(0);
        }

        return vertexList;
    }

    public VertexList xmlFilepathToVertexList(String xmlFileName, String pathToXmlFile, LabelHash labelHash) throws ParserConfigurationException, SAXException, IOException, ExceptionMessage {
        String xmlFilePath;

        // init vars
        vertexList = new VertexList();
        options = new Options();
        ListHelper ListHelper = new ListHelper();

        // get xml vertex list
        xmlFilePath = pathToXmlFile + xmlFileName;
        xmlDoc = xmlFilepathToDoc(xmlFilePath, labelHash);
        vertexList = docToVertexList(xmlDoc, xmlFileName, labelHash);
        return vertexList;
    }

    public VertexList xmlFileToVertexList(File file, LabelHash labelHash) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        xmlDoc = xmlFileToDoc(file);
        vertexList = docToVertexList(xmlDoc, null, labelHash);
        return vertexList;
    }

    // xml parser strategy from https://stackoverflow.com/posts/13296027/revisions
    public VertexList docToVertexList(Document doc, String xmlFileName, LabelHash labelHash) throws ExceptionMessage {
        VertexList vertexList;
        Integer distanceFromRoot;
        Node root;

        vertexList = new VertexList();
        distanceFromRoot = -1;
        root = doc.getFirstChild();
        docToVertexListRecursive(doc, vertexList, root, null, distanceFromRoot, xmlFileName, true, labelHash);
        return vertexList;
    }

    private void docToVertexListRecursive(Document doc, VertexList vertexList, Node node, Vertex parentVertex, Integer distanceFromRoot, String xmlFileName, Boolean isInitial, LabelHash labelHash) throws ExceptionMessage {
        NodeList children;
        int numChildren;
        boolean hasStepDeclarationChild;

        children = node.getChildNodes();
        numChildren = children.getLength();

        hasStepDeclarationChild = false;
        for (int i=0; i<numChildren; i++) {
            Node child = children.item(i);
            String nodeName = child.getNodeName();
            if (nodeName != null && nodeName.equals("step-declaration")) {
                hasStepDeclarationChild = true;
            }
        }
        if (hasStepDeclarationChild) {
            distanceFromRoot++;
        }

        int numSibling = -1;
        for (int i=0; i<numChildren; i++) {
            Node childNode = children.item(i);
            String nodeName = childNode.getNodeName();
            short nodeType = childNode.getNodeType();

            if (nodeName.equals("step-declaration")) {
                numSibling++;
                int vertexNum = vertexList.getList().size();
                Vertex vertex = nodeToVertex(childNode, parentVertex, vertexNum, distanceFromRoot, numSibling, xmlFileName, isInitial, labelHash);

                Integer transitionTargetNum = vertex.getTransitionTo();
                Vertex transitionTarget = new Vertex();
                if (transitionTargetNum != null) {
                    for (Vertex thisVertex : vertexList.getList()) {
                        Integer thisVertexId = thisVertex.getXmlId();
                        if (thisVertexId != null && thisVertexId == transitionTargetNum) {
                            transitionTarget = thisVertex;
                        }
                    }
                    vertex.addChild(transitionTarget);
                    transitionTarget.addParent(vertex);
                    vertex.addRelation(new Relation(vertex, transitionTarget));
                }

                vertexList.addVertex(vertex);
                if (vertexList.getRoot() == null) {
                    vertexList.setRoot(vertex);
                }

                // adding this vertex to parent's children
                if (parentVertex != null) {
                    parentVertex.addChild(vertex);
                    parentVertex.addOrigChild(vertex);
                }

                // making this vertex the parent for the next children
                docToVertexListRecursive(doc, vertexList, childNode, vertex, distanceFromRoot, xmlFileName, false, labelHash);

            } else if (nodeType == 1) {
                docToVertexListRecursive(doc, vertexList, childNode, parentVertex, distanceFromRoot, xmlFileName, isInitial, labelHash);
            }

        }

    }

    public Vertex nodeToVertex(Node node, Vertex parentVertex, int vertexNum, int distanceFromRoot, int numSibling, String xmlFileName, Boolean isInitial, LabelHash labelHash) throws ExceptionMessage {
        Vertex vertex;
        NamedNodeMap nodeAttributes;
        int numAttributes;
        String nodeName;
        String nodeBlurb;
        String nodeKind;
        String property;
        String properties;
        VertexKind vertexKind;
        ArrayList<String> nodeProperties;
        ArrayList<Vertex> nodeParents;
        ArrayList<Label> vertexLabels;
        String[] propArr;
        Integer parentSiblingNum;
        Integer id;
        Integer transitionTo;

        nodeAttributes = node.getAttributes();
        numAttributes = nodeAttributes.getLength();

        nodeProperties = null;
        if (nodeAttributes != null && numAttributes > 0) {

            if (hasNodeAttribute(node, "name")) {
                nodeBlurb = nodeAttributes.getNamedItem("name").getNodeValue();
            } else {
                throw new ExceptionMessage("controller.ReadXml.java " + getLineNumber() + ": Input XML file " + xmlFileName + " appears to have a step-declaration tag missing a name attribute.");
            }

            if (hasNodeAttribute(node, "kind")) {
                nodeKind = nodeAttributes.getNamedItem("kind").getNodeValue();
                vertexKind = VertexKind.toEnum(nodeKind);
            } else {
                throw new ExceptionMessage("controller.ReadXml.java " + getLineNumber() + ": Input XML file " + xmlFileName + " appears to have a step-declaration tag missing a kind attribute.");
            }

            if (hasNodeAttribute(node, "property")) {
                nodeProperties = new ArrayList<String>();
                property = nodeAttributes.getNamedItem("property").getNodeValue();
                nodeProperties.add(property);
            } else if (hasNodeAttribute(node, "properties")) {
                nodeProperties = new ArrayList<String>();
                // from https://www.java67.com/2017/09/how-to-convert-comma-separated-string-to-ArrayList-in-java-example.html
                properties = nodeAttributes.getNamedItem("properties").getNodeValue();
                propArr = properties.split(",");
                nodeProperties = new ArrayList<String>(Arrays.asList(propArr));
            }

            if (hasNodeAttribute(node, "id")) {
                String idWithUnderscore = nodeAttributes.getNamedItem("id").getNodeValue();
                id = Integer.parseInt(idWithUnderscore.substring(1));
            }

            if (hasNodeAttribute(node, "transitionTo")) {
                transitionTo = Integer.parseInt(nodeAttributes.getNamedItem("transitionTo").getNodeValue());
            }

            // properties & labels
            vertexLabels = new ArrayList<>();
            if (nodeProperties != null) {
                for (String thisProperty : nodeProperties) {
                    Label thisLabel = labelHash.addPropertyReturnLabel(thisProperty);
                    if (thisLabel != null) {
                        vertexLabels.add(thisLabel);
                    }
                }
            }




            nodeName = "s" + vertexNum;
            if (parentVertex != null) {
                nodeParents = new ArrayList<Vertex>(Arrays.asList(parentVertex));
            } else {
                nodeParents = null;
            }


            if (nodeParents == null) {
                parentSiblingNum = null;
            } else {
                parentSiblingNum = nodeParents.size() - 1;
            }

            vertex = new Vertex(vertexNum, nodeName, vertexKind, null, nodeBlurb, nodeProperties, vertexLabels, nodeParents, null, distanceFromRoot, numSibling, parentSiblingNum, vertexNum, nodeParents, null, distanceFromRoot, numSibling, isInitial, true);

        } else {
            throw new ExceptionMessage("controller.ReadXml.java " + getLineNumber() + ": Input XML file " + xmlFileName + " appears to have a step-declaration tag missing attributes.");
        }

        return vertex;
    }

    /*
    public VertexList docToVertexList(Document doc) {
        VertexList tempVertexList;
        Node rootNode;
        String rootType;
        Vertex rootVertex;
        NodeList rootChildren;
        Integer numRootChildren;
        String rootNodeValue;

        tempVertexList = new VertexList();
        rootNode = doc.getFirstChild();
        rootType = rootNode.getNodeName();
        rootChildren = rootNode.getChildNodes();
        numRootChildren = rootChildren.getLength();
        rootNodeValue = rootNode.getNodeValue();

        if (rootType.equals("step-declaration")) {
            if (rootNode.getAttributes() != null && hasAttribute(rootNode, "name") && hasAttribute(rootNode, "kind")) {
                String name = rootNode.getAttributes().getNamedItem("name").getNodeValue();
                String kind = rootNode.getAttributes().getNamedItem("kind").getNodeValue();
                rootVertex = new Vertex(name, kind, 0, 0);
            } else {
                rootVertex = new Vertex("root", "", 0, 0);
            }
            tempVertexList.addVertex(rootVertex);
            tempVertexList.setRoot(rootVertex);
        }

        for (int i=0; i<numRootChildren; i++) {
            Node child = rootChildren.item(i);
            short rootChildType = child.getNodeType(); // 1 is an xml tag, 3 is "text mode", etc
            if (rootChildType == 1) {
                // TODO: uncomment this
                // nodeToVertexRecursive(child, 0, 0);
            }
        }

        return tempVertexList;



    }

    // helpers

    // from http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Check_if_an_Element_Has_Attribute.htm
    public static boolean hasAttribute(Node node, String attr) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes == null || attributes.getNamedItem(attr) == null) {
            return false;
        }
        return true;
    }

     */

    public Document xmlFilepathToDoc(String filepath, LabelHash labelHash) throws IOException, SAXException, ParserConfigurationException {
        File openFile;
        Scanner scanner;
        String content;
        DocumentBuilder docBuilder;
        InputSource inputSource;
        Document doc;

        openFile = new File(filepath);
        scanner = new Scanner(openFile);
        scanner.useDelimiter("\\Z");
        content = scanner.next();
        scanner.close();
        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        inputSource = new InputSource(new StringReader(content));
        doc = docBuilder.parse(inputSource);
        return doc;
    }

    public Document xmlFileToDoc(File file) throws IOException, ParserConfigurationException, SAXException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("\\Z");
        String content = scanner.next();
        scanner.close();
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(content));
        Document doc = docBuilder.parse(inputSource);
        return doc;
    }

    // from http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Check_if_an_Element_Has_Attribute.htm
    public static boolean hasNodeAttribute(Node node, String attr) {
        NamedNodeMap attributes;
        attributes = node.getAttributes();
        if (attributes == null || attributes.getNamedItem(attr) == null) {
            return false;
        }
        return true;
    }

}
