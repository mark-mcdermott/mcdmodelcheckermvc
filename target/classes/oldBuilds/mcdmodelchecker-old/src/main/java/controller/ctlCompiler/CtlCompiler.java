package controller.ctlCompiler;

import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.graphItems.Set;
import controller.dataTypes.graphItems.Vertex;
import controller.dataTypes.ctlHelpers.FormulaType;
import controller.ctlCompiler.CtlCompilerParser.FormulaContext;
import controller.helpers.McdException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;

import static controller.dataTypes.ctlHelpers.FormulaType.*;

public class CtlCompiler extends CtlCompilerBaseVisitor<Set> {

    private String model;
    private Kripke kripke;
    private Boolean getCounterExamples;
    private static Set S;
    private static Set NULLSET;
    private ArrayList<Vertex> statesThatHold;

    public CtlCompiler() { }

    // this constructor does not get counter examples
    public CtlCompiler(String model, Kripke kripke) {
        this.model = model;
        this.kripke = kripke;
        this.getCounterExamples = getCounterExamples;
        S = new Set(kripke.S);
        NULLSET = new Set();
    }

    // this is called by the below evaluateModel() function
    @Override public Set visitFormula(FormulaContext ctx) {

        // CTL algos adapted from Huth & Ryan Logic In Computer Science 2nd ed, Cambridge University Press 2012.
        // SATCounterExample Algo adapted from Logic In CS book on page 227

        Set resultSet = new Set();

        FormulaContext formula = ctx;
        try {
            return SAT(formula);
        } catch (McdException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public Set evaluateModel() {
        CtlCompilerParser parser = createParser(model);
        ParseTree tree = parser.formula();
        return this.visit(tree); // this calls the above visitFormula() function in the umbrella case of formula
        // return visitFormula(tree);
    }

    CtlCompilerParser createParser(String model) {
        CharStream stream = CharStreams.fromString(model);
        CtlCompilerLexer lexer = new CtlCompilerLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        return new CtlCompilerParser(tokenStream);
    }

    public Set getStatesThatHold() {
        Set resultSet = evaluateModel();
        return resultSet;
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

    public static Set EG(Set Φ1) throws McdException {
        return negate(AF(negate(Φ1)));
    }

    public static Set AF(Set Φ) throws McdException {
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
        return y;
    }

    public static Set SATAF(Set Φ1) throws McdException {
        Set x;
        Set y;
        x = S;
        y = Φ1;
        while (!x.equals(y)) {
            x = y.copy();
            y = y.union(preA(y));
        }
        return y;
    }

    public static Set SAT(FormulaContext formula) throws McdException {

        FormulaType Φ = formulaType(formula);

        FormulaContext Φ1Ctx;
        FormulaContext Φ2Ctx;
        Set Φ1;
        Set Φ2;

        switch (Φ) {
            case TAUTOLOGY:
                return S;
            case CONTRADICTION:
                return NULLSET;
            case ATOMIC:
                Label label = getLabel(formula);
                return S.statesContaining(label);
            case NEGATE:
                if (formula.not() != null) {
                    Φ1Ctx = formula.not().Φ1;  // Φ1 is a variable set from the grammar file
                    return S.minus(SAT(Φ1Ctx));
                }
            case AND:
                Φ1Ctx = formula.conj().and().Φ1;
                Φ2Ctx = formula.conj().and().Φ2;
                return SAT(Φ1Ctx).intersect(SAT(Φ2Ctx));
            case OR:
                Φ1Ctx = formula.conj().or().Φ1;
                Φ2Ctx = formula.conj().or().Φ2;
                return SAT(Φ1Ctx).union(SAT(Φ2Ctx));
            case IMPLIES:
                Φ1Ctx = formula.imp().Φ1;
                Φ1 = SAT(Φ1Ctx);
                Φ2Ctx = formula.imp().Φ2;
                // return SAT(Φ1Ctx).or(SAT(Φ2Ctx));
                return negate(Φ1).or(SAT(Φ2Ctx)); // i believe the above line was a mistake of mine. fixed 9/7
            case AX:
                Φ1Ctx = formula.temp().ax().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return negate(EX(negate(Φ1)));
            case EX:
                Φ1Ctx = formula.temp().ex().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return SATEX(Φ1);
            case AU:
                Φ1Ctx = formula.temp().au().Φ1;
                Φ2Ctx = formula.temp().au().Φ2;
                Φ1 = SAT(Φ1Ctx);
                Φ2 = SAT(Φ2Ctx);
                return negate(EU(negate(Φ2),negate(Φ1).and(negate(Φ2))).or(EG(negate(Φ2))));
            case EU:
                Φ1Ctx = formula.temp().eu().Φ1;
                Φ2Ctx = formula.temp().eu().Φ2;
                Φ1 = SAT(Φ1Ctx);
                Φ2 = SAT(Φ2Ctx);
                return SATEU(Φ1,Φ2);
            case EF:
                Φ1Ctx = formula.temp().ef().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return EU(tautology(),Φ1);
            case EG:
                Φ1Ctx = formula.temp().eg().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return negate(AF(negate(Φ1)));
            case AF:
                Φ1Ctx = formula.temp().af().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return SATAF(Φ1);
            case AG:
                Φ1Ctx = formula.temp().ag().Φ1;
                Φ1 = SAT(Φ1Ctx);
                return negate(EF(negate(Φ1)));
            default:
                return null;
        }

    }

    // helpers

    private static Label getLabel(CtlCompilerParser.FormulaContext formula) throws McdException {
        return new Label(formula.atom().LABEL().getText());
    }

    private static FormulaType formulaType(CtlCompilerParser.FormulaContext formula) {
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
