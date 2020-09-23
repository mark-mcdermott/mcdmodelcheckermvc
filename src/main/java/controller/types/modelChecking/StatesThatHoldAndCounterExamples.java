package controller.types.modelChecking;

import controller.types.graph.Set;

public class StatesThatHoldAndCounterExamples {

    Set statesThatHoldForModel;
    // Set statesThatHoldForProperty;
    CounterExamples counterExamples;

    public Set getStatesThatHoldForModel() {
        return statesThatHoldForModel;
    }

    public void setStatesThatHoldForModel(Set statesThatHoldForModel) {
        this.statesThatHoldForModel = statesThatHoldForModel;
    }

    public CounterExamples getCounterExamples() { return counterExamples; }

    public void setCounterExamples(CounterExamples counterExamples) {
        this.counterExamples = counterExamples;
    }

//    public Set getStatesThatHoldForProperty() {
//        return statesThatHoldForProperty;
//    }
//
//    public void setStatesThatHoldForProperty(Set statesThatHoldForProperty) {
//        this.statesThatHoldForProperty = statesThatHoldForProperty;
//    }

}
