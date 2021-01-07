package controller.types.analyzer.analyzerData;

import controller.types.graph.Vertex;
import controller.types.modelChecking.CheckedModel;


public class AnalyzerData {

    // AppState appState;
    Selections selections;
    ListsContent listsContent;
    GraphsContent graphsContent;
    CheckedModel checkedModel;
    Integer numProperties;

    public AnalyzerData(Selections selections, ListsContent listsContent, GraphsContent graphsContent, CheckedModel checkedModel, Integer numProperties) {
        // this.appState = appState;
        this.selections = selections;
        this.listsContent = listsContent;
        this.graphsContent = graphsContent;
        this.checkedModel = checkedModel;
        this.numProperties = numProperties;
    }

    // public AppState getAppState() { return appState; }
    // public void setAppState(AppState appState) { this.appState = appState; }
    // public String getStateStr() { return appState.toString(); }

    public Selections getSelections() {
        return selections;
    }

    public void setFileSelections(String[] files) {
        getSelections().setFiles(files);
    }

    public void setDisplaySelections(DisplayType displayType) {
        getSelections().setDisplay(displayType);
    }
    public void setStepSelections(Integer step) {
        getSelections().setStep(step);
    }
    public void setModelSelections(String model) {
        getSelections().setModel(model);
    }
    public void setLoopSelections(Integer loops) {
        getSelections().setLoop(loops);
    }
    public void setStateSelections(Vertex state) {
        getSelections().setState(state);
    }

    public void setSelections(Selections selections) {
        this.selections = selections;
    }

    public ListsContent getListsContent() {
        return listsContent;
    }

    public void setListsContent(ListsContent listsContent) {
        this.listsContent = listsContent;
    }

    public String[] getListsContentFiles() {
        return getListsContent().getFiles();
    }

    public void setListsContentFiles(String[] files) {
        getListsContent().setFiles(files);
    }

    public String[] getListsContentDisplays() {
        return getListsContent().getDisplays();
    }

    public void setListsContentDisplays(String[] displays) {
        getListsContent().setDisplays(displays);
    }

    public Integer[] getListsContentSteps () {
        return getListsContent().getSteps();
    }

    public void setListsContentSteps(Integer[] steps) {
        getListsContent().setSteps(steps);
    }

    public String[] getListsContentModels() {
        return getListsContent().getModels();
    }

    public void setListsContentModels(String[] models) {
        getListsContent().setModels(models);
    }

    public int getListsContentLoops() {
        return getListsContent().getLoops();
    }

    public void setListsContentLoops(int loops) {
        getListsContent().setLoops(loops);
    }
    public Vertex[] getListsContentStates() {
        return getListsContent().getStates();
    }

    public void setListsContentStates(Vertex[] states) {
        getListsContent().setStates(states);
    }
    public String[] getListsContentLabels() {
        return getListsContent().getLabels();
    }

    public void setListsContentLabels(String[] labels) {
        getListsContent().setLabels(labels);
    }

    public GraphsContent getGraphsContent() {
        return graphsContent;
    }

    public void setGraphsContent(GraphsContent graphsContent) {
        this.graphsContent = graphsContent;
    }

    public CheckedModel getCheckedModel() {
        return checkedModel;
    }

    public void setCheckedModel(CheckedModel checkedModel) {
        this.checkedModel = checkedModel;
    }

    public void setDoesHold(String doesHold) {
        getListsContent().setDoesHold(doesHold);
    }

    public void setStatesThatHold(String statesThatHold) {
        getListsContent().setStatesThatHold(statesThatHold);
    }
    public void setCounterExample(String counterExample) {
        getListsContent().setCounterExample(counterExample);
    }
    public void setModelTime(String modelTime) {
        getListsContent().setModelCheckTime(modelTime);
    }

}
