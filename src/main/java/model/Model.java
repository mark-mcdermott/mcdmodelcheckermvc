package model;

import controller.types.data.*;

import java.util.Observable;

public class Model extends Observable {

    Data data;

    public Model() { }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        this.setChanged();
        this.notifyObservers(data.getStateStr()); // send new app state to view
    }

    public AppState getAppState() {
        return getData().getAppState();
    }

    public void setAppState(AppState appState) {
        getData().setAppState(appState);
    }

    public Selections getSelections() {
        return getData().getSelections();
    }

    public void setSelections(Selections selections) {
        getData().setSelections(selections);
    }

    public String[] getSelectedFiles() { return getData().getSelections().getFiles(); }

    public void setSelectedFiles(String[] files) {
        getData().getSelections().setFiles(files);
    }

    public DisplayType getSelectedDisplay() {
        return getData().getSelections().getDisplay();
    }

    public void setSelectedDisplay(DisplayType display) {
        getData().getSelections().getDisplay();
    }

    public String getSelectedStep() {
        return getData().getSelections().getStep();
    }

    public void setSelectedStep(String step) {
        getData().getSelections().setStep(step);
    }

    public String getSelectedModel() {
        return getData().getSelections().getModel();
    }

    public void setSelectedModel(String model) {
        getData().getSelections().setModel(model);
    }

    public Integer getSelectedLoop() {
        return getData().getSelections().getLoop();
    }

    public void setSelectedLoop(Integer loop) {
        getData().getSelections().setLoop(loop);
    }

    public ListsContent getListsContent() {
        return getData().getListsContent();
    }

    public void setListsContent(ListsContent listsContent) {
        getData().setListsContent(listsContent);
    }

    public String[] getFiles() {
        return getData().getListsContent().getFiles();
    }

    public String[] getDisplays() {
        return getData().getListsContent().getDisplays();
    }

    public String[] getSteps() {
        return getData().getListsContent().getSteps();
    }

    public String[] getModels() {
        return getData().getListsContent().getModels();
    }

    public Integer getLoops() {
        return getData().getListsContent().getLoops();
    }

    public String[] getStates() {
        return getData().getListsContent().getStates();
    }

    public String[] getLabels() {
        return getData().getListsContent().getLabels();
    }

    public void setFiles(String[] files) {
        getData().getListsContent().setFiles(files);
    }

    public void setDisplays(String[] displays) {
        getData().getListsContent().setDisplays(displays);
    }

    public void setSteps(String[] steps) {
        getData().getListsContent().setSteps(steps);
    }

    public void setModels(String[] models) {
        getData().getListsContent().setModels(models);
    }

    public void setLoops(Integer loops) {
        getData().getListsContent().setLoops(loops);
    }

    public void setStates(String[] states) {
        getData().getListsContent().setStates(states);
    }

    public void setLabels(String[] labels) {
        getData().getListsContent().setLabels(labels);
    }

}
