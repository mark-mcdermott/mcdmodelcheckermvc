package controller.types.data;

public class Selections {

    private String[] files;
    private DisplayType display;
    private Integer step;
    private String model;
    private Integer loop;
    private String state;

    public Selections(String[] files, DisplayType display, Integer step, String model, Integer loop, String state) {
        this.files = files;
        this.display = display;
        this.step = step;
        this.model = model;
        this.loop = loop;
        this.state = state;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public DisplayType getDisplay() {
        return display;
    }

    public void setDisplay(DisplayType display) {
        this.display = display;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getLoop() {
        return loop;
    }

    public void setLoop(Integer loop) {
        this.loop = loop;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
