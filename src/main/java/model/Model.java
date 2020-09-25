package model;

import controller.types.ctl.Kripke;
import controller.types.analyzer.analyzerData.*;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.types.modelChecking.CheckedModel;
import controller.types.tester.testerData.TesterData;
import controller.utils.ExceptionMessage;

import java.io.File;
import java.util.Observable;

public class Model extends Observable {

    // TODO i want to pull appState out of analyzerData and put it on the top level vars here

    AnalyzerData analyzerData;
    TesterData testerData;
    File[] xmlFileCache;

    public Model() { }

    // setter that notifies observers (view) of the changes (so view can update with the new data)
    public void setAnalyzerData(AnalyzerData analyzerData) {
        this.analyzerData = analyzerData;
        this.setChanged();
        this.notifyObservers(analyzerData.getStateStr()); // send new app state to view
    }

    public void setTesterData(TesterData testerData) {
        this.testerData = testerData;
        this.setChanged();
        this.notifyObservers();
    }

    // generic getters/setters

    public AnalyzerData getAnalyzerData() {
        return analyzerData;
    }

    public File[] getFilesCache() {
        return xmlFileCache;
    }

    public void setXmlFileCache(File[] xmlFileCache) {
        this.xmlFileCache = xmlFileCache;
    }

    public AppState getAppState() {
        return getAnalyzerData().getAppState();
    }

    public void setAppState(AppState appState) {
        getAnalyzerData().setAppState(appState);
    }

    public Selections getSelections() {
        return getAnalyzerData().getSelections();
    }

    public void setSelections(Selections selections) {
        getAnalyzerData().setSelections(selections);
    }

    public String[] getSelectedFiles() {
        if (getAnalyzerData() == null || getAnalyzerData().getSelections() == null || getAnalyzerData().getSelections().getFiles() == null) {
            new ExceptionMessage("Null in either data, selections of files in getSelectedFiles(), Model.java");
        }
        return getAnalyzerData().getSelections().getFiles();
    }

    public void setSelectedFiles(String[] files) {
        getAnalyzerData().getSelections().setFiles(files);
    }

    public DisplayType getSelectedDisplay() {
        return getAnalyzerData().getSelections().getDisplay();
    }

    public void setSelectedDisplay(DisplayType display) {
        getAnalyzerData().getSelections().getDisplay();
    }

    public Integer getSelectedStep() {
        return getAnalyzerData().getSelections().getStep();
    }

    public void setSelectedStep(Integer step) {
        getAnalyzerData().getSelections().setStep(step);
    }

    public String getSelectedModel() {
        return getAnalyzerData().getSelections().getModel();
    }

    public void setSelectedModel(String model) {
        getAnalyzerData().getSelections().setModel(model);
    }

    public Integer getSelectedLoop() {
        return getAnalyzerData().getSelections().getLoop();
    }

    public void setSelectedLoop(Integer loop) {
        getAnalyzerData().getSelections().setLoop(loop);
    }

    public ListsContent getListsContent() {
        return getAnalyzerData().getListsContent();
    }

    public void setListsContent(ListsContent listsContent) {
        getAnalyzerData().setListsContent(listsContent);
    }

    public String[] getFiles() {
        return getAnalyzerData().getListsContent().getFiles();
    }

    public String[] getDisplays() {
        return getAnalyzerData().getListsContent().getDisplays();
    }

    public Integer[] getSteps() {
        return getAnalyzerData().getListsContent().getSteps();
    }

    public String[] getModels() {
        return getAnalyzerData().getListsContent().getModels();
    }

    public Integer getLoops() {
        return getAnalyzerData().getListsContent().getLoops();
    }

    public Vertex[] getStates() {
        return getAnalyzerData().getListsContent().getStates();
    }

    public String[] getLabels() {
        return getAnalyzerData().getListsContent().getLabels();
    }

    public void setFiles(String[] files) {
        getAnalyzerData().getListsContent().setFiles(files);
    }

    public void setDisplays(String[] displays) {
        getAnalyzerData().getListsContent().setDisplays(displays);
    }

    public void setSteps(Integer[] steps) {
        getAnalyzerData().getListsContent().setSteps(steps);
    }

    public void setModels(String[] models) {
        getAnalyzerData().getListsContent().setModels(models);
    }

    public void setLoops(Integer loops) {
        getAnalyzerData().getListsContent().setLoops(loops);
    }

    public void setStates(Vertex[] states) {
        getAnalyzerData().getListsContent().setStates(states);
    }

    public void setLabels(String[] labels) {
        getAnalyzerData().getListsContent().setLabels(labels);
    }

    public GraphsContent getGraphsContent() { return getAnalyzerData().getGraphsContent(); }

    public VertexList getXmlVertexList() {
        return getAnalyzerData().getGraphsContent().getXmlVertList();
    }

    public VertexList getTranslationVertexList() {
        return getAnalyzerData().getGraphsContent().getTransVertList();
    }

    public VertexList getInterleavingsVertexList() {
        return getAnalyzerData().getGraphsContent().getInterVertList();
    }

    public Kripke getXmlKripke() {
        return getAnalyzerData().getGraphsContent().getXmlKripke();
    }

    public Kripke getTranslationKripke() {
        return getAnalyzerData().getGraphsContent().getTransKripke();
    }

    public Kripke getInterleavingsKripke() {
        return getAnalyzerData().getGraphsContent().getInterKripke();
    }

    public LabelHash getLabelHash() {
        return getAnalyzerData().getGraphsContent().getLabelHash();
    }

    public VertexList[] getStepGraphs() { return getAnalyzerData().getGraphsContent().getStepGraphs(); }

    public VertexList getStepGraphSelectedStepMinusOne() {
        return getAnalyzerData().getGraphsContent().getStepGraphs()[0];
    }

    public VertexList getStepGraphSelectedStep() {
        return getAnalyzerData().getGraphsContent().getStepGraphs()[1];
    }

    public String[] getLabelDisplay() {
        return getAnalyzerData().getGraphsContent().getLabelHash().getLabelDisplayListArr();
    }

    public Vertex getSelectedState() {
        return getAnalyzerData().getSelections().getState();
    }

    public CheckedModel getCheckedModel() {
        return getAnalyzerData().getCheckedModel();
    }

    public String getDoesHold() {
        return getAnalyzerData().getListsContent().getDoesHold();
    }

    public String getStatesThatHold() {
        return getAnalyzerData().getListsContent().getStatesThatHold();
    }

    public String getCounterExample() {
        return getAnalyzerData().getListsContent().getCounterExample();
    }

    public String getTime() {
        return getAnalyzerData().getListsContent().getTime();
    }

    public void setDoesHold(String doesHold) {
        getAnalyzerData().getListsContent().setDoesHold(doesHold);
    }

    public void setStatesThatHold(String statesThatHold) {
        getAnalyzerData().getListsContent().setStatesThatHold(statesThatHold);
    }
    public void setCounterExample(String counterExample) {
        getAnalyzerData().getListsContent().setCounterExample(counterExample);
    }
    public void setTime(String time) {
        getAnalyzerData().getListsContent().setTime(time);
    }

}
