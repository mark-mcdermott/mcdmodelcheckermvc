package controller.types.modelChecking;

import controller.types.graph.Vertex;

import java.util.ArrayList;

public class CounterExamples {

    ArrayList<CounterExample> counterExamples;

    public CounterExamples() {
        this.counterExamples = new ArrayList<>();
    };

    public CounterExamples(Vertex state) {
        CounterExample counterExample = new CounterExample(state);
        this.counterExamples = new ArrayList<>();
        this.counterExamples.add(counterExample);
    }

    public CounterExamples(CounterExample counterExample) {
        this.counterExamples = new ArrayList<>();
        this.counterExamples.add(counterExample);
    }

    public CounterExamples(ArrayList<CounterExample> counterExamples) {
        this.counterExamples = counterExamples;
    }

    public void add(CounterExample counterExample) {
        this.counterExamples.add(counterExample);
    }



    // getters & setters

    public ArrayList<CounterExample> getCounterExamples() { return counterExamples; }

    public void setCounterExamples(ArrayList<CounterExample> counterExamples) { this.counterExamples = counterExamples; }


}
