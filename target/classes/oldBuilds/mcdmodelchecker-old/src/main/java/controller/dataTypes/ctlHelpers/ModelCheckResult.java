package controller.dataTypes.ctlHelpers;

import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphHelpers.LabelHash;
import controller.dataTypes.graphItems.Set;
import controller.dataTypes.graphItems.Vertex;

import java.util.ArrayList;

public class  ModelCheckResult {

    private String model;
    private Kripke kripke;
    private Vertex stateToCheck;
    private Integer loopsNum;
    private Set statesThatHold;
    private ArrayList<String> statesThatHoldStrArrList;
    private CounterExamples counterExampleStates;
    private float stopWatchTime;
    private String resultStr;
    private LabelHash labelHash;
    private StatesThatHoldForModel statesThatHoldForModel;

    public ModelCheckResult(
            String model,
            Kripke kripke,
            String stateToCheckStr,
            Integer loopsNum,
            Set statesThatHold,
            ArrayList<String> getStatesThatHoldStrArrList,
            CounterExamples counterExampleStates,
            float stopWatchTime,
            String resultStr,
            LabelHash labelHash,
            StatesThatHoldForModel statesThatHoldForModel
    ) {
        this.model = model;
        this.kripke = kripke;
        this.loopsNum = loopsNum;
        this.stateToCheck = stateToCheck;
        this.statesThatHold = statesThatHold;
        this.statesThatHoldStrArrList = statesThatHoldStrArrList;
        this.counterExampleStates = counterExampleStates;
        this.stopWatchTime = stopWatchTime;
        this.resultStr = resultStr;
        this.labelHash = labelHash;
        this.statesThatHoldForModel = statesThatHoldForModel;
    }

    public Kripke getKripke() {
        return kripke;
    }

    public Set getStatesThatHold() {
        return statesThatHold;
    }

    public CounterExamples getCounterExampleStates() {
        return counterExampleStates;
    }

    public String getModel() {
        return model;
    }

    public Vertex getStateToCheck() {
        return stateToCheck;
    }

    public float getStopWatchTime() {
        return stopWatchTime;
    }

    public void setKripke(Kripke kripke) {
        this.kripke = kripke;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCounterExampleStates(CounterExamples counterExampleStates) {
        this.counterExampleStates = counterExampleStates;
    }

    public void setStatesThatHold(Set statesThatHold) {
        this.statesThatHold = statesThatHold;
    }

    public void setStateToCheck(Vertex stateToCheck) {
        this.stateToCheck = stateToCheck;
    }

    public void setStopWatchTime(float stopWatchTime) {
        this.stopWatchTime = stopWatchTime;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

    public void setLabelHash(LabelHash labelHash) {
        this.labelHash = labelHash;
    }

    public Integer getLoopsNum() {
        return loopsNum;
    }

    public void setLoopsNum(Integer loopsNum) {
        this.loopsNum = loopsNum;
    }

    public ArrayList<String> getStatesThatHoldStrArrList() {
        return statesThatHoldStrArrList;
    }

    public StatesThatHoldForModel getStatesThatHoldForModel() {
        return statesThatHoldForModel;
    }

}
