// Generated from /Users/markmcdermott/Desktop/mcdmodelchecker/src/main/antlr4/controller/ctlCompilerWithCounterExamples/CtlCompilerWithCounterExamples.g4 by ANTLR 4.8

    package controller.ctlCompiler;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CtlCompilerWithCounterExamplesParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CtlCompilerWithCounterExamplesVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(CtlCompilerWithCounterExamplesParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#not}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(CtlCompilerWithCounterExamplesParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#conj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConj(CtlCompilerWithCounterExamplesParser.ConjContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(CtlCompilerWithCounterExamplesParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(CtlCompilerWithCounterExamplesParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#imp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImp(CtlCompilerWithCounterExamplesParser.ImpContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#temp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemp(CtlCompilerWithCounterExamplesParser.TempContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ax}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAx(CtlCompilerWithCounterExamplesParser.AxContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEx(CtlCompilerWithCounterExamplesParser.ExContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#af}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAf(CtlCompilerWithCounterExamplesParser.AfContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEf(CtlCompilerWithCounterExamplesParser.EfContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#ag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAg(CtlCompilerWithCounterExamplesParser.AgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEg(CtlCompilerWithCounterExamplesParser.EgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#au}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAu(CtlCompilerWithCounterExamplesParser.AuContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#eu}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEu(CtlCompilerWithCounterExamplesParser.EuContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(CtlCompilerWithCounterExamplesParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(CtlCompilerWithCounterExamplesParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#taut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTaut(CtlCompilerWithCounterExamplesParser.TautContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerWithCounterExamplesParser#cont}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCont(CtlCompilerWithCounterExamplesParser.ContContext ctx);
}