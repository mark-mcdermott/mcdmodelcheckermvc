package controller.content;

import controller.analyzer.ReadXml;
import controller.analyzer.Translate;
import controller.types.ctl.Kripke;
import controller.types.data.GraphsContent;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetGraphs {

    private GraphsContent graphsContent;
    private LabelHash labelHash;
    private Model model;

    public GetGraphs() { }

    // public GraphsContent getGraphsFromXmlFilenames(String[] xmlFilenames, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
    public GraphsContent getGraphsFromXmlFilenames(String[] xmlFilenames, Boolean isStepSelected, int numLoops, File[] xmlFileCache, Integer selectedStep) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {

        // init vars necessary for getting the vertex lists
        LabelHash labelHash = new LabelHash();
        ReadXml readXml = new ReadXml();
        Translate translate = new Translate();
        // Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Boolean prevStep = false;

        // get xml, translation & interleavings vertex lists
        VertexList xmlVertList;
        // if only one xml file selected, get xmlVertexList normally
        if (xmlFilenames.length == 1) {
            String xmlFilename = xmlFilenames[0];
            File xmlFile = getCacheFileFromFilename(xmlFilename, xmlFileCache);
            xmlVertList = readXml.xmlFileToVertexList(xmlFile, labelHash);
        // if multiple xml files are selected, make each xmlVertexList and merge them together with a new root vertex
        } else {
            xmlVertList = getJointVertexListFromXmlFilenames(xmlFilenames, labelHash, xmlFileCache);
        }
        VertexList transVertList = getTransVertList(xmlVertList, translate, numLoops, isStepSelected, prevStep, selectedStep);
        VertexList interVertList = getInterVertList(xmlVertList, translate, numLoops, isStepSelected, prevStep, selectedStep);

        // get xml, translation & interleavings kripke structures
        Kripke xmlKripke = new Kripke(xmlVertList);
        Kripke transKripke = new Kripke(transVertList);
        Kripke interKripke = new Kripke(interVertList);

        return new GraphsContent(xmlVertList, transVertList, interVertList, xmlKripke, transKripke, interKripke, labelHash);
    }

    private VertexList getJointVertexListFromXmlFilenames(String[] xmlFilenames, LabelHash labelHash, File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        File[] xmlFiles = getFilesFromFilenames(xmlFilenames, xmlFileCache);
        VertexList[] xmlVertexLists = getXmlVertexListsFromXmlFiles(xmlFiles, labelHash);
        VertexList jointXmlVertexList = joinXmlVertexLists(xmlVertexLists);
        return jointXmlVertexList;
    }

    private VertexList[] getXmlVertexListsFromXmlFiles(File[] files, LabelHash labelHash) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        ReadXml readXml = new ReadXml();
        int numFiles = files.length;
        VertexList[] xmlVertLists = new VertexList[numFiles];
        for (int i=0; i<numFiles; i++) {
            xmlVertLists[i] = readXml.xmlFileToVertexList(files[i], labelHash);
        }
        return xmlVertLists;
    }

    private VertexList joinXmlVertexLists(VertexList[] xmlVertexLists) {

        // create new root
        Vertex newRoot = new Vertex();
        VertexList newVertexList = new VertexList();
        newRoot.createRoot(newVertexList);

        // for each vertex list
        for (int i=0; i<xmlVertexLists.length; i++) {

            // get vertex list
            VertexList thisXmlVertexList = xmlVertexLists[i];

            // hook up the new root to the old root of this vertex list
            Vertex thisXmlVertListOldRoot = thisXmlVertexList.getRoot();
            newRoot.linkChildAndRelation(thisXmlVertListOldRoot);

            // set isRoot on old root to false
            thisXmlVertListOldRoot.setIsRoot(false);

            // add all vertices to new vertex list
            for (Vertex vertex : thisXmlVertexList.getList()) {
                newVertexList.addVertex(vertex);
            }
        }
        return newVertexList;
    }

    private VertexList getTransVertList(VertexList xmlVertList, Translate translate, int numLoops, Boolean isStepSelected, Boolean prevStep, Integer selectedStep) throws ExceptionMessage {
        VertexList transVertList = translate.getTransVertListNoInter(xmlVertList, numLoops, isStepSelected, prevStep, selectedStep);
        return transVertList;
    }

    private VertexList getInterVertList(VertexList xmlVertList, Translate translate, int numLoops, Boolean isStepSelected, Boolean prevStep, Integer selectedStep) throws ExceptionMessage {
        VertexList transVertList = translate.getTransVertListWithInters(xmlVertList, numLoops, isStepSelected, prevStep, null);
        return transVertList;
    }

    private File getCacheFileFromFilename(String filename, File[] xmlFileCache) {
        int numFiles = xmlFileCache.length;
        for (int i=0; i<numFiles; i++) {
            File thisFileFromCache = xmlFileCache[i];
            String thisFilenameFromCacheWithPath = thisFileFromCache.toString();
            String[] thisFilenameFromCacheArr = thisFilenameFromCacheWithPath.split("/");
            String thisFilenameFromCache = thisFilenameFromCacheArr[thisFilenameFromCacheArr.length - 1];
            if (thisFilenameFromCache.equals(filename)) {
                return xmlFileCache[i];
            }
        }
        new ExceptionMessage("Xml file not found in xmlFileCache. GetGraphs.java");
        return null;
    }

    private File[] getFilesFromFilenames(String[] filenames, File[] xmlFileCache) {
        int numFiles = filenames.length;
        File[] files = new File[numFiles];
        for (int i=0; i<numFiles; i++) {
            files[i] = getCacheFileFromFilename(filenames[i], xmlFileCache);
        }
        return files;
    }

}
