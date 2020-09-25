package controller.types.tester;

import controller.types.modelChecking.StatesThatHoldForModel;

import java.util.ArrayList;

public class AllStatesThatHoldForAllModels {

    ArrayList<StatesThatHoldForModel> allStatesThatHoldForAllModelsArrList;
    ArrayList<String> allStatesThatHoldForAllModelsStrArrList;

    public AllStatesThatHoldForAllModels(ArrayList<StatesThatHoldForModel> allStatesThatHoldForAllModelsArrList) {
        this.allStatesThatHoldForAllModelsArrList = allStatesThatHoldForAllModelsArrList;

        this.allStatesThatHoldForAllModelsStrArrList = new ArrayList<>();
        for (StatesThatHoldForModel statesThatHoldForModel : allStatesThatHoldForAllModelsArrList) {
            String statesThatHoldForModelStr = statesThatHoldForModel.toString();
            allStatesThatHoldForAllModelsStrArrList.add(statesThatHoldForModelStr);
        }

    }

    public ArrayList<StatesThatHoldForModel> getAllStatesThatHoldForAllModelsArrList() {
        return allStatesThatHoldForAllModelsArrList;
    }

    public ArrayList<String> getAllStatesThatHoldForAllModelsStrArrList() {
        return allStatesThatHoldForAllModelsStrArrList;
    }

}
