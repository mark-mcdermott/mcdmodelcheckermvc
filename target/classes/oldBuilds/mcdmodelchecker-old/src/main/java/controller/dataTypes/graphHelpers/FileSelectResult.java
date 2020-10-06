package controller.dataTypes.graphHelpers;

import _options.Options;
import controller.ReadKrp;
import controller.Translate;
// import controller.TranslateOld;
import controller.dataTypes.graphItems.Kripke;
import controller.helpers.ListHelper;
import controller.helpers.McdException;
import controller.ReadXml;
import model.Model;
import view.View;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class FileSelectResult {

    private ArrayList<String> selectedFiles;
    private DisplayGraphsEnum selectedDisplayGraph;
    private Integer selectedStep;
    private VertexList xmlVertexList;
    private VertexList krpVertexList;
    private VertexList translationVertexListOld;
    private VertexList translationVertexList;
    private VertexList interleavingsVertexList;
    private Integer translationsSteps;
    private Integer interleavingsSteps;
    private LabelHash labelHash;
    private Kripke xmlKripke;
    private Kripke translationKripke;
    private Kripke interleavingsKripke;
    private ArrayList<String> stateDisplayList;
    private ArrayList<String> labelDisplayList;
    private int numProperties;
    private String[] modelList;

    public FileSelectResult(String[] selectedXmlFiles, DisplayGraphsEnum selectedDisplayGraph, Integer selectedStep, Model model, View view, Options options, Boolean isStepSelected, Boolean prevStep) throws SAXException, McdException, ParserConfigurationException, IOException {
        ReadXml readXml = new ReadXml();
        ReadKrp readKrp = new ReadKrp();
        Translate translate = new Translate(model, view);
        ListHelper listHelper = new ListHelper();
        int numLoops = model.getAnalyzerLoopsNumber();
        // System.out.println(numLoops);

        this.selectedFiles = listHelper.stringArrToArrList(selectedXmlFiles);
        this.selectedDisplayGraph = selectedDisplayGraph;
        this.labelHash = new LabelHash();
        this.selectedStep = selectedStep; // this selectedStep argument could just be a boolean i think - the actual number isn't used. instead it's pulled from the model state i think.
        if (selectedStep == null) {
            isStepSelected = false;
        }

        if (this.selectedFiles.get(0).endsWith(".ljx")) {
            this.xmlVertexList = readXml.convertXmlToVertexList(selectedXmlFiles, labelHash);
            this.translationVertexList = translate.getTranslatedVertexListNoInterleavings(xmlVertexList, numLoops, isStepSelected, prevStep);
            this.xmlKripke = new Kripke(xmlVertexList);
            this.translationKripke = new Kripke(translationVertexList);
            this.interleavingsVertexList = translate.getTranslatedVertexListWithInterleavings(xmlVertexList, numLoops, isStepSelected, prevStep);
        }
        if (this.selectedFiles.get(0).endsWith(".krp")) {
            this.krpVertexList = readKrp.convertKrpToVertexList(selectedFiles.get(0), labelHash);
            this.interleavingsVertexList = this.krpVertexList;
        }

        if (selectedStep == null) {
            this.translationsSteps = translationVertexList.getNumTotalSteps();
            this.interleavingsSteps = interleavingsVertexList.getNumTotalSteps();
        }

        // this.interleavingsVertexList = translate.getTranslatedVertexListWithInterleavings(xmlVertexList);
        this.interleavingsKripke = new Kripke(interleavingsVertexList);
        // System.out.println(selectedFiles.toString());
        // System.out.println(interleavingsKripke.toString());
        // System.out.println();
        this.stateDisplayList = listHelper.vertexListToStateArrList(interleavingsVertexList);
        this.labelDisplayList = labelHash.getLabelDisplayOutput();
        this.numProperties = labelHash.getNumProperties();
        this.modelList = listHelper.getModelList(numProperties, options);

        /*
        System.out.println("-- " + selectedFiles.toString() + " --");
        System.out.println("xml");
        System.out.println(xmlKripke.toString());
        System.out.println();
        System.out.println("translation");
        System.out.println(translationKripke.toString());
        System.out.println();
        System.out.println("interleavings");
        System.out.println(interleavingsKripke.toString());
        System.out.println();
        System.out.println();
        */

    }

    public ArrayList<String> getStateDisplayList() {
        return stateDisplayList;
    }

    public ArrayList<String> getLabelDisplayList() {
        return labelDisplayList;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

    public VertexList getTranslationVertexListOld() {
        return translationVertexListOld;
    }

    public VertexList getTranslationVertexList() {
        return translationVertexList;
    }

    public VertexList getXmlVertexList() {
        return xmlVertexList;
    }

    public VertexList getInterleavingsVertexList() {
        return interleavingsVertexList;
    }

    public ArrayList<String> getSelectedFiles() {
        return selectedFiles;
    }

    public Kripke getInterleavingsKripke() {
        return interleavingsKripke;
    }

    public Kripke getXmlKripke() {
        return xmlKripke;
    }

    public Kripke getTranslationKripke() {
        return translationKripke;
    }

    public int getNumProperties() {
        return numProperties;
    }

    public void setNumProperties(int numProperties) {
        this.numProperties = numProperties;
    }

    public String[] getModelList() {
        return modelList;
    }

    public DisplayGraphsEnum getSelectedDisplayGraph() {
        return selectedDisplayGraph;
    }

    public void setSelectedDisplayGraph(DisplayGraphsEnum selectedDisplayGraph) {
        this.selectedDisplayGraph = selectedDisplayGraph;
    }

    public Integer getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(Integer selectedStep) {
        this.selectedStep = selectedStep;
    }



}
