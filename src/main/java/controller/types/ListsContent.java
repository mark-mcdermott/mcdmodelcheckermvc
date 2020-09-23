package controller.types;

public class ListsContent {

    String[] files;
    String[] displays;
    String[] steps;
    String[] models;
    Integer loops;
    String[] states;
    String[] labels;

    public ListsContent(String[] files, String[] displays, String[] steps, String[] models, Integer loops, String[] states, String[] labels) {
        this.files = files;
        this.displays = displays;
        this.steps = steps;
        this.models = models;
        this.loops = loops;
        this.states = states;
        this.labels = labels;
    }

    public String[] getFiles() {
        return files;
    }

    public String[] getDisplays() {
        return displays;
    }

    public String[] getSteps() {
        return steps;
    }

    public String[] getModels() {
        return models;
    }

    public Integer getLoops() {
        return loops;
    }

    public String[] getStates() {
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

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public void setModels(String[] models) {
        this.models = models;
    }

    public void setLoops(Integer loops) {
        this.loops = loops;
    }

    public void setStates(String[] states) {
        this.states = states;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

}
