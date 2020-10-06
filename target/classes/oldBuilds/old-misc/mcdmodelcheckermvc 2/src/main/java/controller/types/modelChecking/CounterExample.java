package controller.types.modelChecking;

import controller.types.graph.Vertex;

import java.util.ArrayList;

public class CounterExample {

    ArrayList<Vertex> counterExample;

    public CounterExample() {
        this.counterExample = new ArrayList<>();
    };

    public CounterExample(Vertex state) {
        this.counterExample = new ArrayList<>();
        this.counterExample.add(state);
    }

    public CounterExample(ArrayList<Vertex> counterExample) {
        this.counterExample = counterExample;
    }

    public void addVertex(Vertex vertex) {
        this.counterExample.add(vertex);
    }

    public CounterExample copy() {
        CounterExample copy = new CounterExample();
        ArrayList<Vertex> states = this.getCounterExample();
        for (Vertex state : states) {
            copy.addVertex(state.copyVertex());
        }
        return copy;
    }


    // getters & setters

    public ArrayList<Vertex> getCounterExample() {
        return counterExample;
    }

    public void setCounterExample(ArrayList<Vertex> counterExample) {
        this.counterExample = counterExample;
    }

}
