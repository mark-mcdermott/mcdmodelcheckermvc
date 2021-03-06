package controller;

import _options.Options;
import controller.ctlCompilerWithCounterExamples.CtlCompilerWithCounterExamples;
import controller.dataTypes.ctlHelpers.*;
import controller.dataTypes.graphHelpers.LabelHash;
import controller.dataTypes.graphItems.GraphModelObj;
import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphItems.Set;
import controller.dataTypes.graphItems.Vertex;
import controller.helpers.McdException;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static controller.helpers.LineNumber.getLineNumber;

public class ModelChecker {

    private ModelCheckResult modelCheckResult;

    public ModelChecker(
            String model,
            Kripke kripke,
            Vertex stateToCheck,
            int loopsNum,
            LabelHash labelHash
    ) {
        float stopWatchTime = 0;
        Set statesThatHold;
        ArrayList<String> statesThatHoldStrArrList;
        CounterExamples counterExamplePaths;
        String resultStr = "";
        String resultStrWithNumSatisfyingStates = "";
        StatesThatHoldForModel statesThatHoldForModel;

        StatesThatHoldAndCounterExamples statesThatHoldAndCounterExamples;

        counterExamplePaths = new CounterExamples();
        // this.model = model;
        // this.kripke = kripke;
        // this.stateToCheckStr = stateToCheck.getName();
        // this.loopsNum = loopsNum;
        // this.labelHash = labelHash;

        String stateToCheckStr = stateToCheck.toString();
        // stateToCheck = new VertexHelper().vertexStringToVertex(stateToCheckStr, kripke.S);
        Instant stopWatchStart = Instant.now();

        // ** eval the model (without getting counterexamples) **
        // statesThatHold = new CtlCompiler(model, kripke).getStatesThatHold();
        // statesThatHoldStrArrList = getStatesThatHoldStrArrList(statesThatHold);
        // statesThatHoldForModel = new StatesThatHoldForModel(model, statesThatHoldStrArrList);
        // Boolean doesStateHold = doesStateHold(statesThatHold, stateToCheck);
        // Instant stopWatchEnd = Instant.now();
        // Double stopWatchSecs = durationToSecs(stopWatchStart, stopWatchEnd);
        // resultStr = getResultStr(stateToCheck, doesStateHold, model, labelHash, stopWatchSecs, counterExamplePaths);

        // ** eval the model (and get counterexamples) **
        statesThatHoldAndCounterExamples = new CtlCompilerWithCounterExamples(model, kripke, stateToCheck).getStatesThatHoldAndCounterExamples();
        statesThatHold = statesThatHoldAndCounterExamples.getStatesThatHoldForModel();
        statesThatHoldStrArrList = getStatesThatHoldStrArrList(statesThatHold);
        // statesThatHoldForModel = new StatesThatHoldForModel(model, statesThatHoldStrArrList);
        statesThatHoldForModel = new StatesThatHoldForModel(model, statesThatHold);
        Boolean doesStateHold = doesStateHold(statesThatHold, stateToCheck);
        Instant stopWatchEnd = Instant.now();
        Double stopWatchSecs = durationToSecs(stopWatchStart, stopWatchEnd);
        counterExamplePaths = statesThatHoldAndCounterExamples.getCounterExamples();
        // System.out.println("to change results string back, ModelChecker:69");
        resultStr = getResultStr(stateToCheck, doesStateHold, statesThatHoldForModel, model, labelHash, stopWatchSecs, counterExamplePaths);
        // resultStr = getResultStrWithNumSatisfyingNodes(stateToCheck, doesStateHold, model, labelHash, stopWatchSecs, counterExamplePaths, kripke);


        this.modelCheckResult = new ModelCheckResult(
                model,
                kripke,
                stateToCheckStr,
                loopsNum,
                statesThatHold,
                statesThatHoldStrArrList,
                counterExamplePaths,
                stopWatchTime,
                resultStr,
                labelHash,
                statesThatHoldForModel
        );
    }

    private ArrayList<String> getStatesThatHoldStrArrList(Set statesThatHold) {
        ArrayList<String> statesThatHoldStrArrList = new ArrayList<>();
        for (Vertex vertex : statesThatHold.states()) {
            statesThatHoldStrArrList.add(vertex.getName());
        }
        return statesThatHoldStrArrList;
    }

    private Boolean doesStateHold(Set statesThatHold, Vertex stateToCheck) {
        String stateToCheckStr = stateToCheck.getName();
        Boolean holds = false;
        if (statesThatHold == null || statesThatHold.states().size() == 0) {
            holds = false;
        } else {
            for (Vertex stateThatHolds : statesThatHold.states()) {
                if (stateThatHolds.getName().equals(stateToCheckStr)) {
                    holds = true;
                }
            }
        }
        return holds;
    }

    private String getHoldsString(Boolean holds) {
        String holdsStr = "";
        if (holds != null) {
            if (holds) holdsStr = holdsStr + "holds";
            else holdsStr = holdsStr + "does not hold";
            return holdsStr;
        } else {
            new McdException("controller.modelchecker.ModelChecker.java " + getLineNumber() + " getHoldsString has null holds parameter.");
        }
        return holdsStr;
    }

    private String getCounterExampleStr(CounterExamples counterExamples) {
        ArrayList<CounterExample> counterExamplesArrList = counterExamples.getCounterExamples();
        String counterExampleStr = "\n\n";
        Boolean justStates = false;
        if (counterExamples == null) return counterExampleStr;
        else {
            if (counterExamples.getCounterExamples().size() == 1) {
                // if just one state
                if (counterExamplesArrList.get(0).getCounterExample().size() == 1) {
                    counterExampleStr = counterExampleStr + "Counter example state: ";
                // if just one path
                } else {
                    counterExampleStr = counterExampleStr + "Counter example path: ";
                }
            // if multiple paths that each only have one state
            } else {
                Boolean multPathsOneState = true;
                for (CounterExample counterExample : counterExamplesArrList) {
                    if (counterExample.getCounterExample().size() > 1) {
                        multPathsOneState = false;
                    }
                }
                if (multPathsOneState) {
                    counterExampleStr = counterExampleStr + "Counter example states: ";
                    justStates = true;
                } else {
                    counterExampleStr = counterExampleStr + "Counter example paths: ";
                }
            }
            if (justStates) {
                for (int i = 0; i < counterExamples.getCounterExamples().size(); i++) {
                    CounterExample counterExample = counterExamples.getCounterExamples().get(i);
                    ArrayList<Vertex> counterExampleArrList = counterExample.getCounterExample();
                    for (int j = 0; j < counterExampleArrList.size(); j++) {
                        counterExampleStr = counterExampleStr + counterExampleArrList.get(j).getName();
                    }
                    if (i < counterExamplesArrList.size() - 1) {
                        counterExampleStr = counterExampleStr + ",";
                    }
                }
            } else {
                for (int i = 0; i < counterExamplesArrList.size(); i++) {
                    CounterExample counterExample = counterExamplesArrList.get(i);
                    ArrayList<Vertex> counterExampleArrList = counterExample.getCounterExample();
                    for (int j = 0; j < counterExampleArrList.size(); j++) {
                        counterExampleStr = counterExampleStr + counterExampleArrList.get(j).getName();
                        if (j < counterExampleArrList.size() - 1) {
                            counterExampleStr = counterExampleStr + ", ";
                        }
                    }
                    counterExampleStr = counterExampleStr + "\n";
                }
            }
        }
        return counterExampleStr;
    }

    private String getResultStr(Vertex stateToCheck, Boolean doesStateHold, StatesThatHoldForModel statesThatHoldForModel, String model, LabelHash labelHash, Double stopWatchSecs, CounterExamples counterExamplePaths) {
        String resultStr = "";
        String counterExampleStr;
        String numHoldingStatesStr;
        String holdingStatesStr;
        int maxLineLength = new Options().getAnalyzerResultsLineLength();
        String state = stateToCheck.getName();
        String holdsOrNot = getHoldsString(doesStateHold);
        String forModel = "for " + model;
        resultStr = state + " " + holdsOrNot + " " + forModel + ".";
        resultStr = wrapResultText(resultStr, maxLineLength);
        if (counterExamplePaths != null) {
            counterExampleStr = getCounterExampleStr(counterExamplePaths);
        } else {
            counterExampleStr = "";
        }
        counterExampleStr = wrapCounterExampleText(counterExampleStr, maxLineLength);
        if (!doesStateHold) {
            resultStr = resultStr + counterExampleStr;
        }
        // numHoldingStatesStr = "Num holding states: " + statesThatHoldForModel.getStatesThatHoldStrArrList().size();
        numHoldingStatesStr = "Num holding states: " + statesThatHoldForModel.getStatesThatHoldSet().getNumStates();
        numHoldingStatesStr = wrapNumHoldingStatesStr(numHoldingStatesStr);
        resultStr = resultStr + "\n" + numHoldingStatesStr;

        holdingStatesStr = "Holding states for " + statesThatHoldForModel.toString();
        holdingStatesStr =  wrapHoldingStatesStr(holdingStatesStr);
        resultStr = resultStr + "\n\n" + holdingStatesStr;

        resultStr = resultStr + "\n\ntime: ";
        if (stopWatchSecs < 0.00001) {
            resultStr = resultStr + "< 0.00001";
        } else if (stopWatchSecs < 0.01) {
            resultStr = resultStr + String.format("%.5f", stopWatchSecs);
        } else {
            resultStr = resultStr + String.format("%.2f", stopWatchSecs);
        }
        resultStr = resultStr + " sec";
        return resultStr;
    }

    public String getResultStrWithNumSatisfyingNodes(Vertex stateToCheck, Boolean doesStateHold, String model, LabelHash labelHash, Double stopWatchSecs, CounterExamples counterExamplePaths, Kripke kripke) {
        String resultsStr = "";
        // String resultsStr = getResultStr(stateToCheck,doesStateHold,model,labelHash,stopWatchSecs,counterExamplePaths);
        // ModelCheckResult modelCheckResult = getModelCheckResult();
        StatesThatHoldAndCounterExamples statesThatHoldAndCounterExamples = new CtlCompilerWithCounterExamples(model, kripke, stateToCheck).getStatesThatHoldAndCounterExamples();
        Set statesThatHoldForModel = statesThatHoldAndCounterExamples.getStatesThatHoldForModel();
        int numNodesSatisfyModel = statesThatHoldForModel.getNumStates();
        resultsStr = resultsStr + "\nNumber states satisfying model: " + numNodesSatisfyModel;
        return resultsStr;
    }

    public ModelCheckResult getCtlResult() {
        return modelCheckResult;
    }

    // helpers

    public static Double durationToSecs (Instant startTime, Instant stopTime) {
        Duration stopWatchDuration = Duration.between(startTime, stopTime);
        long stopWatchInNano = stopWatchDuration.toNanos();
        double stopWatchInSec = (double)stopWatchInNano / 1_000_000_000.0;
        return stopWatchInSec;
    }

    // wrap text code from https://stackoverflow.com/a/7528259, accessed 4/23/20
    public String wrapResultText(String input, int maxLineLength) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > maxLineLength) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");
            lineLen += word.length();
        }
        return output.toString();
    }

    public String wrapCounterExampleText(String counterExampleText, int maxLineLength) {
        if (counterExampleText.length() > maxLineLength) {
            counterExampleText.replace("Counter example paths:", "Counter example paths:\n");
        }
        return counterExampleText;
    }

    // placeholder if needed
    public String wrapNumHoldingStatesStr(String numHoldingStates) {
        return numHoldingStates;
    }

    // placeholder if needed
    public String wrapHoldingStatesStr(String holdingStatesStr) {
        return holdingStatesStr;
    }

    public ModelCheckResult getModelCheckResult() {
        return modelCheckResult;
    }



}
