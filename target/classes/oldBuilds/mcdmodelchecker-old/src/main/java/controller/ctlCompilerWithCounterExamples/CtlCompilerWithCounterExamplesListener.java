// Generated from /Users/markmcdermott/Desktop/mcdmodelchecker/src/main/antlr4/controller/ctlCompilerWithCounterExamples/CtlCompilerWithCounterExamples.g4 by ANTLR 4.8

    package controller.ctlCompiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CtlCompilerWithCounterExamplesParser}.
 */
public interface CtlCompilerWithCounterExamplesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(CtlCompilerWithCounterExamplesParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(CtlCompilerWithCounterExamplesParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#not}.
	 * @param ctx the parse tree
	 */
	void enterNot(CtlCompilerWithCounterExamplesParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#not}.
	 * @param ctx the parse tree
	 */
	void exitNot(CtlCompilerWithCounterExamplesParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#conj}.
	 * @param ctx the parse tree
	 */
	void enterConj(CtlCompilerWithCounterExamplesParser.ConjContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#conj}.
	 * @param ctx the parse tree
	 */
	void exitConj(CtlCompilerWithCounterExamplesParser.ConjContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(CtlCompilerWithCounterExamplesParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(CtlCompilerWithCounterExamplesParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(CtlCompilerWithCounterExamplesParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(CtlCompilerWithCounterExamplesParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#imp}.
	 * @param ctx the parse tree
	 */
	void enterImp(CtlCompilerWithCounterExamplesParser.ImpContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#imp}.
	 * @param ctx the parse tree
	 */
	void exitImp(CtlCompilerWithCounterExamplesParser.ImpContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#temp}.
	 * @param ctx the parse tree
	 */
	void enterTemp(CtlCompilerWithCounterExamplesParser.TempContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#temp}.
	 * @param ctx the parse tree
	 */
	void exitTemp(CtlCompilerWithCounterExamplesParser.TempContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ax}.
	 * @param ctx the parse tree
	 */
	void enterAx(CtlCompilerWithCounterExamplesParser.AxContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ax}.
	 * @param ctx the parse tree
	 */
	void exitAx(CtlCompilerWithCounterExamplesParser.AxContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ex}.
	 * @param ctx the parse tree
	 */
	void enterEx(CtlCompilerWithCounterExamplesParser.ExContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ex}.
	 * @param ctx the parse tree
	 */
	void exitEx(CtlCompilerWithCounterExamplesParser.ExContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#af}.
	 * @param ctx the parse tree
	 */
	void enterAf(CtlCompilerWithCounterExamplesParser.AfContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#af}.
	 * @param ctx the parse tree
	 */
	void exitAf(CtlCompilerWithCounterExamplesParser.AfContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ef}.
	 * @param ctx the parse tree
	 */
	void enterEf(CtlCompilerWithCounterExamplesParser.EfContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ef}.
	 * @param ctx the parse tree
	 */
	void exitEf(CtlCompilerWithCounterExamplesParser.EfContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ag}.
	 * @param ctx the parse tree
	 */
	void enterAg(CtlCompilerWithCounterExamplesParser.AgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ag}.
	 * @param ctx the parse tree
	 */
	void exitAg(CtlCompilerWithCounterExamplesParser.AgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eg}.
	 * @param ctx the parse tree
	 */
	void enterEg(CtlCompilerWithCounterExamplesParser.EgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eg}.
	 * @param ctx the parse tree
	 */
	void exitEg(CtlCompilerWithCounterExamplesParser.EgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#au}.
	 * @param ctx the parse tree
	 */
	void enterAu(CtlCompilerWithCounterExamplesParser.AuContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#au}.
	 * @param ctx the parse tree
	 */
	void exitAu(CtlCompilerWithCounterExamplesParser.AuContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eu}.
	 * @param ctx the parse tree
	 */
	void enterEu(CtlCompilerWithCounterExamplesParser.EuContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eu}.
	 * @param ctx the parse tree
	 */
	void exitEu(CtlCompilerWithCounterExamplesParser.EuContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(CtlCompilerWithCounterExamplesParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(CtlCompilerWithCounterExamplesParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(CtlCompilerWithCounterExamplesParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(CtlCompilerWithCounterExamplesParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#taut}.
	 * @param ctx the parse tree
	 */
	void enterTaut(CtlCompilerWithCounterExamplesParser.TautContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#taut}.
	 * @param ctx the parse tree
	 */
	void exitTaut(CtlCompilerWithCounterExamplesParser.TautContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#cont}.
	 * @param ctx the parse tree
	 */
	void enterCont(CtlCompilerWithCounterExamplesParser.ContContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#cont}.
	 * @param ctx the parse tree
	 */
	void exitCont(CtlCompilerWithCounterExamplesParser.ContContext ctx);
}