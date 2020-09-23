package controller.content;

import controller.analyzer.ReadXml;
import controller.analyzer.Translate;
import controller.types.data.GraphsContent;
import controller.types.graph.LabelHash;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import model.Model;

import java.io.File;
import java.io.FileNotFoundException;

public class GetGraphs {

    private GraphsContent graphsContent;
    private LabelHash labelHash;
    private Model model;

    public GetGraphs() { }

    public GraphsContent getGraphs(String xmlFilename, Model model) throws FileNotFoundException, ExceptionMessage {

        // init vars necessary for getting the vertex lists
        LabelHash labelHash = new LabelHash();
        Translate translate = new Translate();
        int numLoops = model.getLoops();
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Boolean prevStep = false;

        // get xml, translation & interleavings vertex lists
        VertexList xmlVertList = new ReadXml().xmlFileToVertexList(getFileFromFilename(xmlFilename), labelHash);
        VertexList transVertList = getTransVertList(xmlVertList, translate, numLoops, isStepSelected, prevStep);
        VertexList interVertList = getInterVertList(xmlVertList, translate, numLoops, isStepSelected, prevStep);

        // get xml, translation & interleavings kripke structures


    }

    private VertexList getTransVertList(VertexList xmlVertList, Translate translate, int numLoops, Boolean isStepSelected, Boolean prevStep) throws ExceptionMessage {
        VertexList transVertList = translate.getTransVertListNoInter(xmlVertList, numLoops, isStepSelected, prevStep);
        return transVertList;
    }

    private VertexList getInterVertList(VertexList xmlVertList, Translate translate, int numLoops, Boolean isStepSelected, Boolean prevStep) throws ExceptionMessage {
        VertexList transVertList = translate.getTransVertListWithInters(xmlVertList, numLoops, isStepSelected, prevStep);
        return transVertList;
    }

    private File getFileFromFilename(String filename) {
        File[] xmlFileCache = model.getFilesCache();
        int numFiles = xmlFileCache.length;
        for (int i=0; i<numFiles; i++) {
            if (xmlFileCache[i].toString().equals(filename)) {
                return xmlFileCache[i];
            }
        }
        new ExceptionMessage("Xml file not found in xmlFileCache. GetGraphs.java");
        return null;
    }

}
