package controller.analyzer.ctlCompiler;

import controller.types.ctl.Kripke;
import controller.types.ctl.Label;
import controller.types.graph.Set;
import controller.types.graph.Vertex;
import controller.types.modelChecking.CounterExample;
import controller.types.modelChecking.CounterExamples;
import controller.types.modelChecking.FormulaType;
import controller.types.modelChecking.StatesThatHoldAndCounterExamples;
import controller.utils.ExceptionMessage;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

import static controller.types.modelChecking.FormulaType.*;

public class CtlCompiler extends controller.analyzer.ctlCompiler.CtlCompilerBaseVisitor<StatesThatHoldAndCounterExamples> {

    String model;
    Kripke kripke;
    static Vertex stateToCheck;
    static ArrayList<Vertex> stateToChecksChildren;
    static Set S;
    static Set NULLSET;
    static ArrayList<Vertex> thisPathWorkingList;
    static CounterExamples counterExamplesWorkingList;
    static CounterExamples allPaths;
    static ArrayList<Vertex> allPathsVisited;
    static Boolean firstPathNotAllPFound;
    static ArrayList<Vertex> firstPathNotAllPFoundVisited;
    static Boolean firstGlobalPathWithNoPFound;
    static ArrayList<Vertex> firstGlobalPathWithNoPFoundVisited;
    static Boolean firstPathNotAllPOrQFound;
    static Boolean pFound;


    public CtlCompiler() { }

    public CtlCompiler(String model, Kripke kripke, Vertex stateToCheck) {
        this.model = model;
        this.kripke = kripke;
        this.stateToCheck = stateToCheck;
        this.stateToChecksChildren = stateToCheck.getChildren();
        S = new Set(kripke.S);
        NULLSET = new Set();
    }

    // this is called by the below evaluateModelWithCounterExamples() function
    @Override public StatesThatHoldAndCounterExamples visitFormula(CtlCompilerParser.FormulaContext ctx) throws ExceptionMessage {

        // CTL algos adapted from Huth & Ryan Logic In Computer Science 2nd ed, Cambridge University Press 2012.
        // SATCounterExample Algo adapted from Logic In CS book on page 227

        StatesThatHoldAndCounterExamples result = new StatesThatHoldAndCounterExamples();

        controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext formula = ctx;
        return SAT(formula, S);

        /*
        try {
            return SAT(formula, S);
        } catch (ExceptionMessage e) {
            e.printStackTrace();
        }
        return result;
        */
    }

    public StatesThatHoldAndCounterExamples evaluateModelWithCounterExamples() {
        controller.analyzer.ctlCompiler.CtlCompilerParser parser = createParser(model);
        ParseTree tree = parser.formula();
        return this.visit(tree); // this calls the above visitFormula() function in the umbrella case of formula
        // return visitFormula(tree);
    }

    controller.analyzer.ctlCompiler.CtlCompilerParser createParser(String model) {
        CharStream stream = CharStreams.fromString(model);
        controller.analyzer.ctlCompiler.CtlCompilerLexer lexer = new controller.analyzer.ctlCompiler.CtlCompilerLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        return new controller.analyzer.ctlCompiler.CtlCompilerParser(tokenStream);
    }

    public StatesThatHoldAndCounterExamples getStatesThatHoldAndCounterExamples() {
        StatesThatHoldAndCounterExamples result = evaluateModelWithCounterExamples();
        return result;
    }

    public static Set negate(Set set) {
        return S.minus(set);
    }

    public static Set tautology() {
        return S;
    }

    public static Set EX(Set Φ) {
        return SATEX(Φ);
    }

    public static Set EU(Set Φ1, Set Φ2) {
        return SATEU(Φ1, Φ2);
    }

    public static Set EF(Set Φ) {
        return EU(tautology(), Φ);
    }

    public static Set EG(Set Φ1) throws ExceptionMessage {
        return negate(AF(negate(Φ1)));
    }

    public static Set AF(Set Φ) throws ExceptionMessage {
        return SATAF(Φ);
    }

    public static Set preE(Set sPrime) {
        Set resultSet = new Set();
        for (Vertex sPrimeState : sPrime.states()) {
            if (sPrimeState.getParents() != null) {
                for (Vertex sPrimeParent : sPrimeState.getParents()) {
                    // TODO: can a state be preE(Y) if it's already in Y?
                    // that is, if it's in Y, but leads to another state in Y
                    // if (!resultSet.hasState(sPrimeParent)) {
                    resultSet.add(sPrimeParent);
                    // }
                }
            }
        }
        return resultSet;
    }

    public static Set preA(Set sPrime) {
        Set resultSet = new Set();
        Set preE = preE(sPrime);

        for (Vertex preEVertex : preE.states()) {
            ArrayList<Vertex> preEChildren = preEVertex.getChildren();
            Boolean isPreEVertexPreA = true;
            for (Vertex preEChild : preEChildren) {
                if (!sPrime.hasState(preEChild)) isPreEVertexPreA = false;
            }
            if (isPreEVertexPreA) resultSet.add(preEVertex);
        }
        return resultSet;
    }

    public static Set SATEU(Set Φ1, Set Φ2) {
        Set w;
        Set x;
        Set y;
        w = Φ1;
        x = S;
        y = Φ2;
        int loopNum = 0;
        while (!x.equals(y)) {
            loopNum++;
            x = y.copy();
            y = y.union(w.intersect(preE(y)));

            x.sort();
            y.sort();

        }
        return y;
    }

    public static Set SATEX(Set set) {
        Set x;
        Set y;
        x = set;
        y = preE(x);
        y.sort();
        return y;
    }

    public static Set SATAF(Set Φ1) throws ExceptionMessage {
        Set x;
        Set y;
        x = S;
        y = Φ1;
        while (!x.equals(y)) {
            x = y.copy();
            y = y.union(preA(y));
        }
        y.sort();
        return y;
    }

    // This could probably use some more tests
    static CounterExamples getAllPaths() {
        CounterExample counterExample = new CounterExample();
        CounterExamples counterExamples = new CounterExamples();
        thisPathWorkingList = new ArrayList<>();
        allPaths = new CounterExamples();
        allPathsVisited = new ArrayList<Vertex>();
        allPathsVisited.add(stateToCheck);
        stateToChecksChildren = stateToCheck.getChildren();
        if (stateToChecksChildren == null || stateToChecksChildren.size() == 0) {
            counterExample.addVertex(stateToCheck);
            counterExamples.add(counterExample);
        } else {
            for (Vertex thisChild : stateToChecksChildren) {
                getAllPathsRecursive(thisChild);
            }
        }
        return counterExamples;
    }

    static void getAllPathsRecursive(Vertex state) {
        thisPathWorkingList.add(state);
        // stateToChecksChildren.add(state);
        allPathsVisited.add(state);
        ArrayList<Vertex> children = state.getChildren();
         if (children == null || children.size() == 0 ) {
             CounterExample counterExample = new CounterExample(thisPathWorkingList);
             allPaths.add(counterExample);
             thisPathWorkingList.remove(state);
         } else {
            for (Vertex child : children) {
                if (!allPathsVisited.contains(child)) {
                    getAllPathsRecursive(child);
                }
            }
        }
    }

    static CounterExamples getFirstGlobalPathWithNoP(Set statesThatHold) {
        thisPathWorkingList = new ArrayList<>();
        counterExamplesWorkingList = new CounterExamples();
        firstGlobalPathWithNoPFoundVisited = new ArrayList<>();
        firstGlobalPathWithNoPFound = false;
        thisPathWorkingList.add(stateToCheck);
        firstGlobalPathWithNoPFoundVisited.add(stateToCheck);
        if (stateToChecksChildren != null) {
            for (Vertex child : stateToChecksChildren) {
                if (!firstGlobalPathWithNoPFound) {
                    getFirstGlobalPathWithNoPRecursive(child, statesThatHold);
                }
            }
        } else {
            CounterExample counterExample = new CounterExample(thisPathWorkingList);
            counterExamplesWorkingList.add(counterExample.copy());
        }
        return counterExamplesWorkingList;
    }

    static void getFirstGlobalPathWithNoPRecursive(Vertex state, Set statesThatHoldForModel) {

        thisPathWorkingList.add(state);
        firstGlobalPathWithNoPFoundVisited.add(state);
        // if state isn't p
        if (!statesThatHoldForModel.hasState(state)) {
            ArrayList<Vertex> children = state.getChildren();
            // and state has no children
            if (children == null || children.size() == 0) {
                CounterExample counterExample = new CounterExample(thisPathWorkingList);
                counterExamplesWorkingList.add(counterExample.copy());
                // thisPathWorkingList.remove(state);
                firstGlobalPathWithNoPFound = true;
            } else {
                for (Vertex child : children) {
                    if (!firstGlobalPathWithNoPFound && !firstGlobalPathWithNoPFoundVisited.contains(child)) {
                        getFirstGlobalPathWithNoPRecursive(child, statesThatHoldForModel);
                    }
                }
            }
        } else {
            thisPathWorkingList.remove(state);
        }
    }

    static CounterExamples getAllPathsUntilNoP(Vertex state, Set statesThatHoldForProperty) {
        thisPathWorkingList = new ArrayList<>();
        counterExamplesWorkingList = new CounterExamples();
        getAllPathsUntilNoPRecursive(state, statesThatHoldForProperty);
        return counterExamplesWorkingList;
    }

    static void getAllPathsUntilNoPRecursive(Vertex state, Set statesThatHoldForProperty) {

        thisPathWorkingList.add(state);
        ArrayList<Vertex> children = state.getChildren();
        if (!statesThatHoldForProperty.hasState(state) || children == null || children.size() == 0) {
            CounterExample counterExample = new CounterExample(thisPathWorkingList);
            counterExamplesWorkingList.add(counterExample.copy());
            thisPathWorkingList.remove(state);
        } else {
            for (Vertex child : children) {
                getAllPathsUntilNoPRecursive(child, statesThatHoldForProperty);
            }
        }
    }

    static CounterExamples getFirstPathNotAllP(Set statesThatHoldForModel, Set statesThatHoldForProperty) {
        CounterExample counterExample = new CounterExample();
        thisPathWorkingList = new ArrayList<>();
        firstPathNotAllPFoundVisited = new ArrayList<>();
        counterExamplesWorkingList = new CounterExamples();

        thisPathWorkingList.add(stateToCheck);
        firstPathNotAllPFoundVisited.add(stateToCheck);

        if (!statesThatHoldForProperty.hasState(stateToCheck)) {
            counterExample = new CounterExample(thisPathWorkingList);
            counterExamplesWorkingList.add(counterExample.copy());
        } else {
            if (stateToChecksChildren != null && stateToChecksChildren.size() > 0) {
                firstPathNotAllPFound = false;
                for (Vertex child : stateToChecksChildren) {
                    if (!firstPathNotAllPFound && !firstPathNotAllPFoundVisited.contains(child)) {
                        getFirstPathNotAllPRecursive(child, statesThatHoldForModel, statesThatHoldForProperty);
                    }
                }
            }
        }
        return counterExamplesWorkingList;
    }

    // TODO: not positive but I think this is running infinitely on kripkes with loops - make sure there's a isVisited check
    static void getFirstPathNotAllPRecursive(Vertex state, Set statesThatHoldForModel, Set statesThatHoldForProperty) {

        thisPathWorkingList.add(state);
        firstPathNotAllPFoundVisited.add(state);

        if (statesThatHoldForProperty.hasState(state)) {
            ArrayList<Vertex> children = state.getChildren();
            if (children != null && children.size() > 0) {
                for (Vertex child : children) {
                    if (!firstPathNotAllPFound && !firstPathNotAllPFoundVisited.contains(child)) {
                        getFirstPathNotAllPRecursive(child, statesThatHoldForModel, statesThatHoldForProperty);
                    }
                }
            }
        } else {
            CounterExample counterExample = new CounterExample(thisPathWorkingList);
            counterExamplesWorkingList.add(counterExample.copy());
            // thisPathWorkingList.remove(state);
            firstPathNotAllPFound = true;
        }
    }

    // for AU counter examples
    static void getFirstPathNotAllPOrQRecursive(Vertex state, Set pSet, Set qSet) {

        thisPathWorkingList.add(state);
        //System.out.println(state);

        /* this is buggy
        Boolean stateHasP = pSet.hasState(state);
        Boolean stateHasQ = qSet.hasState(state);
        ArrayList<Vertex> children = state.getChildren();

        // if state has neither p nor q, then counter example found
        if (!stateHasP && !stateHasQ) {
            firstPathNotAllPOrQFound = true;
            return;
            // else if state has p but has no children, then counter example found
        } else if (stateHasP && (children == null || children.size() == 0)) {
            firstPathNotAllPOrQFound = true;
            return;
            // if state has q, then this path is not a counter example so remove state and return
        } else if (stateHasQ) {
            thisPathWorkingList.remove(state);
            return;
            // else state has p and has children, so continue to crawl children recursively
        } else {
            for (Vertex child : children) {
                if (!firstPathNotAllPOrQFound) {
                    getFirstPathNotAllPOrQRecursive(child, pSet, qSet);
                }
            }
        }*/
    }

    // for EU counter examples
    static void getPathsNotAllPOrQ(Vertex state, Set pSet, Set qSet) {
        thisPathWorkingList = new ArrayList<>();
        counterExamplesWorkingList = new CounterExamples();
        thisPathWorkingList.add(state);
        Boolean stateHasP = pSet.hasState(state);
        // if state doesn't have p, then counter example found
        if (!stateHasP) {
            counterExamplesWorkingList.add(new CounterExample(thisPathWorkingList));
            return;
        } else {
            getPathsNotAllPOrQRecursive(state, pSet, qSet);
        }
    }

    // for EU counter examples
    static void getPathsNotAllPOrQRecursive(Vertex state, Set pSet, Set qSet) {
        ArrayList<Vertex> children = state.getChildren();

        //if (children != null) {
            pFound = false;
            for (Vertex child : children) {
                Boolean stateHasP = pSet.hasState(child);
                // if child has p, continue to recursively crawl children
                if (stateHasP) {
                    pFound = true;
                    thisPathWorkingList.add(child);
                    getPathsNotAllPOrQRecursive(child, pSet, qSet);
                }
            }
            // if none of the children have p, then counter examples found - make a counter example path with each
            if (!pFound) {
                for (Vertex child : children) {
                    thisPathWorkingList.add(child);
                    CounterExample counterExample = new CounterExample(thisPathWorkingList);
                    counterExamplesWorkingList.add(counterExample.copy());
                    thisPathWorkingList.remove(child);
                }
            }
        //}

    }

    public static StatesThatHoldAndCounterExamples SAT(controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext formula, Set set) throws ExceptionMessage {

        FormulaType Φ = formulaType(formula);
        StatesThatHoldAndCounterExamples result = new StatesThatHoldAndCounterExamples();
        CounterExample counterExample = new CounterExample();
        CounterExamples counterExamples = new CounterExamples();
        CounterExamples allPaths = new CounterExamples();
        controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext Φ1Ctx;
        controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext Φ2Ctx;
        Set Φ1;
        Set Φ2;

        switch (Φ) {
            case TAUTOLOGY:
                result.setStatesThatHoldForModel(S);
                return result;
            case CONTRADICTION:
                result.setStatesThatHoldForModel(NULLSET);
                counterExample.addVertex(stateToCheck);
                counterExamples.add(counterExample);
                result.setCounterExamples(counterExamples);
                return result;
            case ATOMIC:
                Label label = getLabel(formula);
                result.setStatesThatHoldForModel(S.statesContaining(label));
                counterExample.addVertex(stateToCheck);
                counterExamples.add(counterExample);
                result.setCounterExamples(counterExamples);
                return result;
            case NEGATE:
                if (formula.not() != null) {
                    Φ1Ctx = formula.not().Φ1;  // Φ1 is a variable set from the grammar file
                    Set setToNegate = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                    Set statesThatHold = S.minus(setToNegate);
                    result.setStatesThatHoldForModel(statesThatHold);
                    if (!statesThatHold.hasState(stateToCheck)) {
                        counterExample.addVertex(stateToCheck);
                        counterExamples.add(counterExample);
                        result.setCounterExamples(counterExamples);
                    }
                    return result;
                }
            case AND:
                Φ1Ctx = formula.conj().and().Φ1;
                Φ2Ctx = formula.conj().and().Φ2;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Φ2 = SAT(Φ2Ctx, set).getStatesThatHoldForModel();
                result.setStatesThatHoldForModel(Φ1.intersect(Φ2).sort());
                return result;
            case OR:
                Φ1Ctx = formula.conj().or().Φ1;
                Φ2Ctx = formula.conj().or().Φ2;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Φ2 = SAT(Φ2Ctx, set).getStatesThatHoldForModel();
                result.setStatesThatHoldForModel(Φ1.union(Φ2).sort());
                return result;
            case IMPLIES:
                Φ1Ctx = formula.imp().Φ1;
                Φ2Ctx = formula.imp().Φ2;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Φ2 = SAT(Φ2Ctx, set).getStatesThatHoldForModel();
                // result.setStatesThatHoldForModel(Φ1.or(Φ2));
                result.setStatesThatHoldForModel(negate(Φ1).or(Φ2)); // i believe the above line was a mistake of mine. fixed 9/7.
                return result;
            case AX:
                Φ1Ctx = formula.temp().ax().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Set statesThatHoldForModel = negate(EX(negate(Φ1)));
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    if (stateToChecksChildren != null || stateToChecksChildren.size() == 0) {
                        Boolean foundNonHoldingChild = false;
                        for (Vertex child : stateToChecksChildren) {
                            if (!foundNonHoldingChild) {
                                if (!statesThatHoldForModel.hasState(child) || child.getChildren() == null || child.getChildren().size() == 0) {
                                    counterExample = new CounterExample();
                                    counterExample.addVertex(stateToCheck);
                                    counterExample.addVertex(child);
                                    counterExamples.add(counterExample);
                                    foundNonHoldingChild = true;
                                }
                            }
                        }
                    } else {
                        counterExample.addVertex(stateToCheck);
                        counterExamples.add(counterExample);
                    }
                    result.setCounterExamples(counterExamples);
                }
                return result;
            case EX:
                Φ1Ctx = formula.temp().ex().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                result.setStatesThatHoldForModel(SATEX(Φ1));
                if (stateToChecksChildren != null) {
                    for (Vertex child : stateToChecksChildren) {
                        counterExample = new CounterExample();
                        counterExample.addVertex(stateToCheck);
                        counterExample.addVertex(child);
                        counterExamples.add(counterExample);
                    }
                } else {
                    counterExample = new CounterExample();
                    counterExample.addVertex(stateToCheck);
                    counterExamples.add(counterExample);
                }
                result.setCounterExamples(counterExamples);
                return result;
            case AU:
                Φ1Ctx = formula.temp().au().Φ1;
                Φ2Ctx = formula.temp().au().Φ2;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Φ2 = SAT(Φ2Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = negate(EU(negate(Φ2),negate(Φ1).and(negate(Φ2))).or(EG(negate(Φ2))));
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                thisPathWorkingList = new ArrayList<>();
                firstPathNotAllPOrQFound = false;
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    getFirstPathNotAllPOrQRecursive(stateToCheck, Φ1, Φ2);
                    counterExample = new CounterExample(thisPathWorkingList);
                    counterExamples.add(counterExample);
                    result.setCounterExamples(counterExamples);
                }
                return result;
            case EU:
                Φ1Ctx = formula.temp().eu().Φ1;
                Φ2Ctx = formula.temp().eu().Φ2;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                Φ2 = SAT(Φ2Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = SATEU(Φ1,Φ2);
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    getPathsNotAllPOrQ(stateToCheck, Φ1, Φ2);
                    counterExamples = counterExamplesWorkingList;
                    result.setCounterExamples(counterExamples);
                }
                return result;
            case EF: // Test this more
                Φ1Ctx = formula.temp().ef().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = EU(tautology(),Φ1);
                result.setStatesThatHoldForModel(EU(tautology(),Φ1));
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    if (stateToChecksChildren == null || stateToChecksChildren.size() == 0) {
                        counterExample = new CounterExample(stateToCheck);
                        counterExamples.add(counterExample);
                    } else {
                        counterExamples = getAllPaths();
                    }
                    result.setCounterExamples(counterExamples);
                }
                return result;
            case EG:
                Φ1Ctx = formula.temp().eg().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = negate(AF(negate(Φ1)));
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    counterExamples = getAllPathsUntilNoP(stateToCheck, Φ1);
                }
                result.setCounterExamples(counterExamples);
                return result;
            case AF:
                Φ1Ctx = formula.temp().af().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = SATAF(Φ1);
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                if (!statesThatHoldForModel.hasState(stateToCheck)) {
                    counterExamples = getFirstGlobalPathWithNoP(statesThatHoldForModel);
                }
                result.setCounterExamples(counterExamples);
                return result;
            case AG: // Test this more
                // TODO: rethink this, knowing that Φ1 is states that hold for property, if you need it
                Φ1Ctx = formula.temp().ag().Φ1;
                Φ1 = SAT(Φ1Ctx, set).getStatesThatHoldForModel();
                statesThatHoldForModel = negate(EF(negate(Φ1)));
                result.setStatesThatHoldForModel(statesThatHoldForModel);
                counterExamples = getFirstPathNotAllP(statesThatHoldForModel, Φ1);
                result.setCounterExamples(counterExamples);
                return result;
            default:
                return null;
        }

    }

    // helpers

    private static Label getLabel(controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext formula) throws ExceptionMessage {
        return new Label(formula.atom().LABEL().getText());
    }

    private static FormulaType formulaType(controller.analyzer.ctlCompiler.CtlCompilerParser.FormulaContext formula) {
        FormulaType formulaType = null;
        if (formula.bool() != null) {
            if (formula.bool().taut() != null) {
                formulaType = TAUTOLOGY;
            } else if (formula.bool().cont() != null) {
                formulaType = CONTRADICTION;
            }
        } else if (formula.atom() != null) {
            formulaType = ATOMIC;
        } else if (formula.not() != null) {
            formulaType = NEGATE;
        } else if (formula.conj() != null) {
            if (formula.conj().and() != null) {
                formulaType = AND;
            } else if (formula.conj().or() != null) {
                formulaType = OR;
            }
        } else if (formula.imp() != null) {
            formulaType = IMPLIES;
        } else if (formula.temp() != null) {
            if (formula.temp().ax() != null) {
                formulaType = AX;
            } else if (formula.temp().ex() != null) {
                formulaType = EX;
            } else if (formula.temp().af() != null) {
                formulaType = AF;
            } else if (formula.temp().ef() != null) {
                formulaType = EF;
            } else if (formula.temp().ag() != null) {
                formulaType = AG;
            } else if (formula.temp().eg() != null) {
                formulaType = EG;
            } else if (formula.temp().au() != null) {
                formulaType = AU;
            } else if (formula.temp().eu() != null) {
                formulaType = EU;
            }
        }
        return formulaType;
    }

}
