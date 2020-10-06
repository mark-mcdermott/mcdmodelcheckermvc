package controller.types.tester.testerData;

public class TesterMisc {

    private int numLoops;
    private String[] models;

    public TesterMisc(int numLoops) {
        this.numLoops = numLoops;
    }

    public int getNumLoops() {
        return numLoops;
    }

    public void setNumLoops(int numLoops) {
        this.numLoops = numLoops;
    }

    public String[] getModels() {
        return models;
    }

    public void setModels(String[] models) {
        this.models = models;
    }
}
