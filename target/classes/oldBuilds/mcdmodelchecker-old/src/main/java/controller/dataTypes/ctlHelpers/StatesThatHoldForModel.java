package controller.dataTypes.ctlHelpers;

import java.util.ArrayList;
import java.util.Arrays;

import controller.dataTypes.graphItems.Set;
import controller.dataTypes.graphItems.Vertex;

public class StatesThatHoldForModel {
    String model;
    ArrayList<String> statesThatHoldStrArrList;
    Set statesThatHoldSet;

    public StatesThatHoldForModel(String model, ArrayList<String> statesThatHoldStrArrList) {
        this.model = model;
        this.statesThatHoldStrArrList = statesThatHoldStrArrList;
    }

    public StatesThatHoldForModel(String model, Set statesThatHoldSet) {
        this.model = model;
        this.statesThatHoldSet = statesThatHoldSet;
    }



    public String toString() {
        String statesThatHoldStr = "";
        if (statesThatHoldSet == null) {
            statesThatHoldStr += model + ": {";
            for (int i = 0; i < statesThatHoldStrArrList.size(); i++) {
                statesThatHoldStr += statesThatHoldStrArrList.get(i);
                if (i <= statesThatHoldStrArrList.size() - 2) {
                    statesThatHoldStr += ",";
                }
            }
            statesThatHoldStr += "}";
        } else {
            statesThatHoldStr += model + ": {";
            ArrayList<Vertex> statesThatHold = statesThatHoldSet.states();
            for (int i = 0; i < statesThatHold.size(); i++) {
                Vertex thisState = statesThatHold.get(i);
                statesThatHoldStr += thisState.toStringDetailed();
                if (i <= statesThatHold.size() - 2) {
                    statesThatHoldStr += ",\n";
                }
            }
            statesThatHoldStr += "}";
        }
        return statesThatHoldStr;
    }

    /*
    public String toStringDetailed() {
        String statesThatHoldStr = "";
        statesThatHoldStr += model + ": {";
        for (int i=0; i< statesThatHoldStrArrList.size(); i++) {
            statesThatHoldStr += statesThatHoldStrArrList.get(i) + ":";
            statesThatHoldStr += statesThatHoldStrArrList.get(i).;
            if (i <= statesThatHoldStrArrList.size() - 2) {
                statesThatHoldStr += ",";
            }
        }
        statesThatHoldStr += "}";
        return statesThatHoldStr;
    }
    */

    // getters & setters

    public ArrayList<String> getStatesThatHoldStrArrList() {
        return statesThatHoldStrArrList;
    }

    public Set getStatesThatHoldSet() {
        return statesThatHoldSet;
    }

    public String getModel() {
        return model;
    }

}
