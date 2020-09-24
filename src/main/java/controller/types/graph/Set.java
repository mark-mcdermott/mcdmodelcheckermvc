package controller.types.graph;

import controller.types.ctl.Label;

import java.util.ArrayList;

public class Set {

    private ArrayList<Vertex> states;

    public Set() {
        this.states = new ArrayList<>();
    }

    public Set(Vertex state) {
        this.states = new ArrayList<>();
        this.states.add(state);
    }

    public Set(ArrayList<Vertex> states) {
        this.states = states;
    }

    // set theory/arithmetic operations

    // equals
    public Boolean equals(Set b) {
        Set a = this;
        ArrayList<Vertex> aStates = a.states();
        ArrayList<Vertex> bStates = b.states();
        if (aStates.size() != bStates.size()) return false;
        for (Vertex aVertex : aStates) {
            if (!b.hasState(aVertex)) return false;
        }
        return true;
    }

    // minus
    public Set minus(Set b) {
        Set resultSet = new Set();
        Set a = this;

        for (Vertex state : a.states()) {
            if (!b.hasState(state)) {
                resultSet.add(state);
            }
        }
        return resultSet;
    }

    // { s∈S | Φ∈L(s) }
    public Set statesContaining(Label label) {
        Set resultSet = new Set();
        for (Vertex state : this.states) {
            if (state.hasLabel(label)) {
                resultSet.add(state);
            }
        }
        return resultSet;
    }

    // intersect
    public Set intersect (Set b) {
        Set resultSet = new Set();
        Set a = this;
        for (Vertex state : a.states) {
            if (b.hasState(state)) {
                resultSet.add(state);
            }
        }
        return resultSet;
    }

    // union
    public Set union (Set b) {
        Set resultSet = this;
        for (Vertex state : b.states) {
            resultSet.add(state);
        }
        return  resultSet;
    }

    public Set and (Set b) {
        return this.intersect(b);
    }

    public Set or (Set b) {
        return this.union(b);
    }


    // operations

    public void add(Vertex state) {
        ArrayList<Vertex> states = this.getStates();
        if (!states.contains(state)) {
            states.add(state);
        }
        this.setStates(states);
    }

    // helpers

    public Boolean hasState(Vertex stateToFind) {
        Set set = this;
        for (Vertex thisState : set.getStates()) {
            if (thisState.hasSamePropsAs(stateToFind)) {
                return true;
            }
        }
        return false;
    }

    public Set copy() {
        Set thisSet = this;
        Set copySet = new Set();
        for (Vertex vertex : thisSet.states) {
            Vertex copyVertex = vertex.copyVertex();
            copySet.add(copyVertex);
        }
        return copySet;
    }

    public Set sort() {
        Set set = this;
        ArrayList<Vertex> states = set.states;
        // bubble sort
        for (int i=0; i<states.size(); i++) {
            for (int j=i; j<states.size(); j++) {
                Vertex iState = states.get(i);
                Vertex jState = states.get(j);
                int iNum = iState.getNumber();
                int jNum = jState.getNumber();
                if (jNum < iNum) {
                    Vertex tempState = iState;
                    states.set(i, jState);
                    states.set(j, tempState);
                }
            }
        }
        return set;
    }

    public String toString() {
        String resultStr = "{";
        for (int i=0; i<states.size(); i++) {
            Vertex vertex = states.get(i);
            resultStr = resultStr + vertex.getName();
            if (i<states.size() - 1) {
                resultStr = resultStr + ",";
            }
        }
        resultStr = resultStr + "}";
        return resultStr;
    }

    public int getNumStates() {
        return states.size();
    }

    // getters & setters

    public ArrayList<Vertex> getStates() {
        return states;
    }

    public ArrayList<Vertex> states() {
        return states;
    }

    public void setStates(ArrayList<Vertex> states) {
        this.states = states;
    }

}
