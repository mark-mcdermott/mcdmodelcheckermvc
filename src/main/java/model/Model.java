package model;

import controller.types.data.Data;

import java.util.Observable;

public class Model extends Observable {

    Data data;

    public Model() { }

    public void setData(Data data) {
        this.data = data;
        String appState = data.getStateStr();
        this.setChanged();
        this.notifyObservers(appState);
    }
}
