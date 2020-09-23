package controller.types;

public class Data {

    AppState appState;
    Selections selections;
    ListsContent listsContent;
    GraphsContent graphsContent;
    CheckedModel checkedModel;

    public Data(AppState appState, Selections selections, ListsContent listsContent, GraphsContent graphsContent, CheckedModel checkedModel) {
        this.appState = appState;
        this.selections = selections;
        this.listsContent = listsContent;
        this.graphsContent = graphsContent;
        this.checkedModel = checkedModel;
    }

    public AppState getAppState() {
        return appState;
    }

    public String getStateStr() {
        return appState.toString();
    }

    public Selections getSelections() {
        return selections;
    }

    public String[] getSelectedFiles() {
        return getSelections().getFiles();
    }

    public void setSelectedFiles(String[] files) {
        getSelections().setFiles(files);
    }

    public DisplayType getSelectedDisplay() {
        return getSelections().getDisplay();
    }

    public void setSelectedDisplay(DisplayType display) {
        getSelections().getDisplay();
    }

    public String getSelectedStep() {
        return getSelections().getStep();
    }

    public void setSelectedStep(String step) {
        getSelections().setStep(step);
    }

    public String getSelectedModel() {
        return getSelections().getModel();
    }

    public void setSelectedModel(String model) {
        getSelections().setModel(model);
    }

    public Integer getSelectedLoop() {
        return getSelections().getLoop();
    }

    public void setSelectedLoop(Integer loop) {
        getSelections().setLoop(loop);
    }

    public ListsContent getListsContent() {
        return listsContent;
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

    public String[] getListsContentSteps () {
        return getListsContent().getSteps();
    }

    public void setListsContentSteps(String[] steps) {
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
    public String[] getListsContentStates() {
        return getListsContent().getStates();
    }

    public void setListsContentStates(String[] states) {
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

    public CheckedModel getCheckedModel() {
        return checkedModel;
    }
}
