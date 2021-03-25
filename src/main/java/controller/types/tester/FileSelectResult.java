package controller.types.tester;

import _options.Options;
import controller.analyzer.ReadKrp;
import controller.analyzer.ReadXml;
import controller.analyzer.Translate;
import controller.types.analyzer.analyzerData.DisplayType;
import controller.types.ctl.Kripke;
import controller.types.graph.LabelHash;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import controller.utils.ListHelper;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class FileSelectResult {

    private ArrayList<String> selectedFiles;
    private DisplayType selectedDisplayGraph;
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


    public FileSelectResult(String[] selectedXmlFiles, DisplayType selectedDisplayGraph, Integer selectedStep, Model model, Options options, Boolean isStepSelected, Boolean prevStep) throws SAXException, ExceptionMessage, ParserConfigurationException, IOException, ExceptionMessage, SAXException {
        ReadXml readXml = new ReadXml();
        ReadKrp readKrp = new ReadKrp();
        Translate translate = new Translate(model);
        ListHelper listHelper = new ListHelper();
        int numLoops = model.getLoops();

        this.selectedFiles = listHelper.stringArrToArrList(selectedXmlFiles);
        this.selectedDisplayGraph = selectedDisplayGraph;
        this.labelHash = new LabelHash();
        this.selectedStep = selectedStep; // this selectedStep argument could just be a boolean i think - the actual number isn't used. instead it's pulled from the model state i think.
        if (selectedStep == null) {
            isStepSelected = false;
        }

        if (this.selectedFiles.get(0).endsWith(".ljx")) {
            this.xmlVertexList = readXml.convertXmlToVertexList(selectedXmlFiles, labelHash);
            this.translationVertexList = translate.getTransVertListNoInter(xmlVertexList, numLoops, isStepSelected, prevStep, selectedStep, listHelper.listToStringArray(selectedFiles));
            this.xmlKripke = new Kripke(xmlVertexList);
            this.translationKripke = new Kripke(translationVertexList);
            this.interleavingsVertexList = translate.getTransVertListWithInters(xmlVertexList, numLoops, isStepSelected, prevStep, selectedStep, listHelper.listToStringArray(selectedFiles));
        }
        if (this.selectedFiles.get(0).endsWith(".krp")) {
            this.krpVertexList = readKrp.convertKrpToVertexList(selectedFiles.get(0), labelHash);
            this.interleavingsVertexList = this.krpVertexList;
        }

        if (selectedStep == null) {
            this.translationsSteps = translationVertexList.getNumTotalSteps();
            this.interleavingsSteps = interleavingsVertexList.getNumTotalSteps();
        }

        this.interleavingsKripke = new Kripke(interleavingsVertexList);
        this.stateDisplayList = listHelper.vertexListToStateArrList(interleavingsVertexList);
        this.labelDisplayList = labelHash.getLabelDisplayOutput();
        this.numProperties = labelHash.getNumProperties();
        this.modelList = model.getListsContent().getModels();

    }

    public FileSelectResult(String[] selectedXmlFiles, DisplayType selectedDisplayGraph, Integer selectedStep, Model model, Options options, Boolean isStepSelected, Boolean prevStep, int numLoops) throws SAXException, ExceptionMessage, ParserConfigurationException, IOException, ExceptionMessage, SAXException {
        ReadXml readXml = new ReadXml();
        ReadKrp readKrp = new ReadKrp();
        Translate translate = new Translate(model);
        ListHelper listHelper = new ListHelper();
        // int numLoops = model.getLoops();

        this.selectedFiles = listHelper.stringArrToArrList(selectedXmlFiles);
        this.selectedDisplayGraph = selectedDisplayGraph;
        this.labelHash = new LabelHash();
        this.selectedStep = selectedStep; // this selectedStep argument could just be a boolean i think - the actual number isn't used. instead it's pulled from the model state i think.
        if (selectedStep == null) {
            isStepSelected = false;
        }

        if (this.selectedFiles.get(0).endsWith(".ljx")) {
            this.xmlVertexList = readXml.convertXmlToVertexList(selectedXmlFiles, labelHash);
            this.translationVertexList = translate.getTransVertListNoInter(xmlVertexList, numLoops, isStepSelected, prevStep, selectedStep, listHelper.listToStringArray(selectedFiles));
            this.xmlKripke = new Kripke(xmlVertexList);
            this.translationKripke = new Kripke(translationVertexList);
            this.interleavingsVertexList = translate.getTransVertListWithInters(xmlVertexList, numLoops, isStepSelected, prevStep, selectedStep, listHelper.listToStringArray(selectedFiles));
        }
        if (this.selectedFiles.get(0).endsWith(".krp")) {
            this.krpVertexList = readKrp.convertKrpToVertexList(selectedFiles.get(0), labelHash);
            this.interleavingsVertexList = this.krpVertexList;
        }

        if (selectedStep == null) {
            this.translationsSteps = translationVertexList.getNumTotalSteps();
            this.interleavingsSteps = interleavingsVertexList.getNumTotalSteps();
        }

        this.interleavingsKripke = new Kripke(interleavingsVertexList);
        this.stateDisplayList = listHelper.vertexListToStateArrList(interleavingsVertexList);
        this.labelDisplayList = labelHash.getLabelDisplayOutput();
        this.numProperties = labelHash.getNumProperties();
        this.modelList = model.getListsContent().getModels();

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

    public DisplayType getSelectedDisplayGraph() {
        return selectedDisplayGraph;
    }

    public void setSelectedDisplayGraph(DisplayType selectedDisplayGraph) {
        this.selectedDisplayGraph = selectedDisplayGraph;
    }

    public Integer getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(Integer selectedStep) {
        this.selectedStep = selectedStep;
    }

}
