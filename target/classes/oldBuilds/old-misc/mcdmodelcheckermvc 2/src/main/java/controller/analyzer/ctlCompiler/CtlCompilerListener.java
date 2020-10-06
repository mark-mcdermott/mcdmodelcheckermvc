// Generated from /Users/markmcdermott/Desktop/mcdmodelcheckermvc/src/main/antlr4/controller/analyzer/ctlCompiler/CtlCompiler.g4 by ANTLR 4.8

    package controller.analyzer.ctlCompiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CtlCompilerParser}.
 */
public interface CtlCompilerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(CtlCompilerParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(CtlCompilerParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#not}.
	 * @param ctx the parse tree
	 */
	void enterNot(CtlCompilerParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#not}.
	 * @param ctx the parse tree
	 */
	void exitNot(CtlCompilerParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#conj}.
	 * @param ctx the parse tree
	 */
	void enterConj(CtlCompilerParser.ConjContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#conj}.
	 * @param ctx the parse tree
	 */
	void exitConj(CtlCompilerParser.ConjContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(CtlCompilerParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(CtlCompilerParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(CtlCompilerParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(CtlCompilerParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#imp}.
	 * @param ctx the parse tree
	 */
	void enterImp(CtlCompilerParser.ImpContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#imp}.
	 * @param ctx the parse tree
	 */
	void exitImp(CtlCompilerParser.ImpContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#temp}.
	 * @param ctx the parse tree
	 */
	void enterTemp(CtlCompilerParser.TempContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#temp}.
	 * @param ctx the parse tree
	 */
	void exitTemp(CtlCompilerParser.TempContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#ax}.
	 * @param ctx the parse tree
	 */
	void enterAx(CtlCompilerParser.AxContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#ax}.
	 * @param ctx the parse tree
	 */
	void exitAx(CtlCompilerParser.AxContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#ex}.
	 * @param ctx the parse tree
	 */
	void enterEx(CtlCompilerParser.ExContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#ex}.
	 * @param ctx the parse tree
	 */
	void exitEx(CtlCompilerParser.ExContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#af}.
	 * @param ctx the parse tree
	 */
	void enterAf(CtlCompilerParser.AfContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#af}.
	 * @param ctx the parse tree
	 */
	void exitAf(CtlCompilerParser.AfContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#ef}.
	 * @param ctx the parse tree
	 */
	void enterEf(CtlCompilerParser.EfContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#ef}.
	 * @param ctx the parse tree
	 */
	void exitEf(CtlCompilerParser.EfContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#ag}.
	 * @param ctx the parse tree
	 */
	void enterAg(CtlCompilerParser.AgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#ag}.
	 * @param ctx the parse tree
	 */
	void exitAg(CtlCompilerParser.AgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#eg}.
	 * @param ctx the parse tree
	 */
	void enterEg(CtlCompilerParser.EgContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#eg}.
	 * @param ctx the parse tree
	 */
	void exitEg(CtlCompilerParser.EgContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#au}.
	 * @param ctx the parse tree
	 */
	void enterAu(CtlCompilerParser.AuContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#au}.
	 * @param ctx the parse tree
	 */
	void exitAu(CtlCompilerParser.AuContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#eu}.
	 * @param ctx the parse tree
	 */
	void enterEu(CtlCompilerParser.EuContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#eu}.
	 * @param ctx the parse tree
	 */
	void exitEu(CtlCompilerParser.EuContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(CtlCompilerParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(CtlCompilerParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(CtlCompilerParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(CtlCompilerParser.BoolContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#taut}.
	 * @param ctx the parse tree
	 */
	void enterTaut(CtlCompilerParser.TautContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#taut}.
	 * @param ctx the parse tree
	 */
	void exitTaut(CtlCompilerParser.TautContext ctx);
	/**
	 * Enter a parse tree produced by {@link CtlCompilerParser#cont}.
	 * @param ctx the parse tree
	 */
	void enterCont(CtlCompilerParser.ContContext ctx);
	/**
	 * Exit a parse tree produced by {@link CtlCompilerParser#cont}.
	 * @param ctx the parse tree
	 */
	void exitCont(CtlCompilerParser.ContContext ctx);
}