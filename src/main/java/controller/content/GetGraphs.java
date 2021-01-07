package controller.content;

import controller.analyzer.ReadXml;
import controller.analyzer.Translate;
import controller.types.ctl.Kripke;
import controller.types.analyzer.analyzerData.DisplayType;
import controller.types.analyzer.analyzerData.GraphsContent;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static controller.analyzer.ModelChecker.durationToSecs;
import static controller.types.analyzer.analyzerData.DisplayType.INTER_COMP;
import static controller.types.analyzer.analyzerData.DisplayType.TRANS_COMP;

public class GetGraphs {

    private GraphsContent graphsContent;
    private LabelHash labelHash;
    private Model model;

    public GetGraphs() { }

    public GetGraphs(Model model) {
        this.model = model;
    }

    // public GraphsContent getGraphsFromXmlFilenames(String[] xmlFilenames, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
    public GraphsContent getGraphsFromXmlFilenames(String[] xmlFilenames, DisplayType displayType, Boolean isStepSelected, int numLoops, File[] xmlFileCache, Integer selectedStep) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {

        // init vars necessary for getting the vertex lists
        GraphsContent graphsContent = null;
        LabelHash labelHash = new LabelHash();
        ReadXml readXml = new ReadXml();
        Translate translate = new Translate(model);

        // normal case: get all three completed graphs
        if (!isStepSelected) {
            graphsContent = getAllThreeGraphs(xmlFilenames,readXml,xmlFileCache,translate,numLoops,isStepSelected,selectedStep,labelHash); // TODO uncomment
            // graphsContent = getXmlAndTransGraphs(xmlFilenames,readXml,xmlFileCache,translate,numLoops,isStepSelected,selectedStep,labelHash); // TODO comment

        // stepGraph case: get two graphs of same type (tran/inter) but of two steps (selectedStep & selectedStep - 1)
        } else if (isStepSelected) {

            VertexList xmlVertList;
            if (xmlFilenames.length > 1) {
                new ExceptionMessage("Trying to do step comparison of graph with two input files selected. This functionality is not coded as of now.");
            } else if (xmlFilenames.length == 1) {
                String xmlFilename = xmlFilenames[0];
                File xmlFile = getCacheFileFromFilename(xmlFilename, xmlFileCache);
                xmlVertList = readXml.xmlFileToVertexList(xmlFile, labelHash);

                // DisplayType displayType = model.getSelectedDisplay();
                if (displayType == TRANS_COMP) {

                    VertexList testTransVertListForTotalNumSteps;
                    VertexList transVertListSelectedStep;
                    VertexList transVertListSelectedStepMinusOne;

                    testTransVertListForTotalNumSteps = getTransVertList(xmlVertList, translate, numLoops, false, false, null);
                    transVertListSelectedStep = getTransVertList(xmlVertList, translate, numLoops, isStepSelected, false, selectedStep);
                    transVertListSelectedStepMinusOne = getTransVertList(xmlVertList, translate, numLoops, isStepSelected, true, selectedStep);
                    int numSteps = testTransVertListForTotalNumSteps.getNumTotalSteps();
                    VertexList[] stepGraphs = new VertexList[2];
                    stepGraphs[0] = transVertListSelectedStepMinusOne;
                    stepGraphs[1] = transVertListSelectedStep;
                    graphsContent = new GraphsContent(xmlVertList, labelHash, numSteps, stepGraphs);

                } else if (displayType == INTER_COMP) {

                    VertexList testInterVertListForTotalNumSteps;
                    VertexList interVertListSelectedStep;
                    VertexList interVertListSelectedStepMinusOne;

                    testInterVertListForTotalNumSteps = getInterVertList(xmlVertList, translate, numLoops, false, false, null);
                    interVertListSelectedStep = getInterVertList(xmlVertList, translate, numLoops, isStepSelected, false, selectedStep);
                    interVertListSelectedStepMinusOne = getInterVertList(xmlVertList, translate, numLoops, isStepSelected, true, selectedStep);
                    int numSteps = testInterVertListForTotalNumSteps.getNumTotalSteps();
                    VertexList[] stepGraphs = new VertexList[2];
                    stepGraphs[0] = interVertListSelectedStepMinusOne;
                    stepGraphs[1] = interVertListSelectedStep;
                    graphsContent = new GraphsContent(xmlVertList, labelHash, numSteps, stepGraphs);
                }
            }
        }

        Integer numProps = labelHash.getNumProperties();
        graphsContent.setNumProps(numProps);

        return graphsContent;
    }

    private GraphsContent getAllThreeGraphs(String[] xmlFilenames, ReadXml readXml, File[] xmlFileCache, Translate translate, Integer numLoops, Boolean isStepSelected, Integer selectedStep, LabelHash labelHash) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {

        // start translation time stopwatch
        Instant stopWatchStart = Instant.now();

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

        // start translation time stopwatch
        Instant stopWatchEnd = Instant.now();
        Double translationTime = durationToSecs(stopWatchStart, stopWatchEnd);

        GraphsContent graphsContent = new GraphsContent(xmlVertList, transVertList, interVertList, xmlKripke, transKripke, interKripke, labelHash, translationTime);
        return graphsContent;
    }

    private GraphsContent getXmlAndTransGraphs(String[] xmlFilenames, ReadXml readXml, File[] xmlFileCache, Translate translate, Integer numLoops, Boolean isStepSelected, Integer selectedStep, LabelHash labelHash) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
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
        // VertexList interVertList = getInterVertList(xmlVertList, translate, numLoops, isStepSelected, prevStep, selectedStep);

        // get xml, translation & interleavings kripke structures
        Kripke xmlKripke = new Kripke(xmlVertList);
        Kripke transKripke = new Kripke(transVertList);
        // Kripke interKripke = new Kripke(interVertList);

        GraphsContent graphsContent = new GraphsContent(xmlVertList, transVertList, null, xmlKripke, transKripke, null, labelHash, null);
        return graphsContent;
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
        VertexList interVertList = translate.getTransVertListWithInters(xmlVertList, numLoops, isStepSelected, prevStep, selectedStep);
        return interVertList;
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
