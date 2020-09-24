package controller.types.data;

import controller.types.graph.Vertex;

public class ListsContent {

    String[] files;
    String[] displays;
    Integer[] steps;
    String[] models;
    Integer loops;
    Vertex[] states;
    String[] labels;
    String doesHold;
    String statesThatHold;
    String counterExample;
    String time;

    public ListsContent(String[] files, String[] displays, Integer[] steps, String[] models, Integer loops, Vertex[] states, String[] labels) {
        this.files = files;
        this.displays = displays;
        this.steps = steps;
        this.models = models;
        this.loops = loops;
        this.states = states;
        this.labels = labels;
        this.doesHold = null;
        this.statesThatHold = null;
        this.counterExample = null;
        this.time = null;
    }

    public String[] getFiles() {
        return files;
    }

    public String[] getDisplays() {
        return displays;
    }

    public Integer[] getSteps() {
        return steps;
    }

    public String[] getModels() {
        return models;
    }

    public Integer getLoops() {
        return loops;
    }

    public Vertex[] getStates() {
        return states;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public void setDisplays(String[] displays) {
        this.displays = displays;
    }

    public void setSteps(Integer[] steps) {
        this.steps = steps;
    }

    public void setModels(String[] models) {
        this.models = models;
    }

    public void setLoops(Integer loops) {
        this.loops = loops;
    }

    public void setStates(Vertex[] states) {
        this.states = states;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getCounterExample() {
        return counterExample;
    }

    public String getDoesHold() {
        return doesHold;
    }

    public String getStatesThatHold() {
        return statesThatHold;
    }

    public String getTime() {
        return time;
    }

    public void setCounterExample(String counterExample) {
        this.counterExample = counterExample;
    }

    public void setDoesHold(String doesHold) {
        this.doesHold = doesHold;
    }

    public void setStatesThatHold(String statesThatHold) {
        this.statesThatHold = statesThatHold;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
