// Generated from /Users/markmcdermott/Desktop/mcdmodelcheckermvc/src/main/antlr4/controller/analyzer/ctlCompiler/CtlCompiler.g4 by ANTLR 4.8

    package controller.analyzer.ctlCompiler;

import controller.utils.ExceptionMessage;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CtlCompilerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CtlCompilerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(CtlCompilerParser.FormulaContext ctx) throws ExceptionMessage;
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#not}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot(CtlCompilerParser.NotContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#conj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConj(CtlCompilerParser.ConjContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(CtlCompilerParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(CtlCompilerParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#imp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImp(CtlCompilerParser.ImpContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#temp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemp(CtlCompilerParser.TempContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#ax}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAx(CtlCompilerParser.AxContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#ex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEx(CtlCompilerParser.ExContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#af}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAf(CtlCompilerParser.AfContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#ef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEf(CtlCompilerParser.EfContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#ag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAg(CtlCompilerParser.AgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#eg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEg(CtlCompilerParser.EgContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#au}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAu(CtlCompilerParser.AuContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#eu}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEu(CtlCompilerParser.EuContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(CtlCompilerParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(CtlCompilerParser.BoolContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#taut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTaut(CtlCompilerParser.TautContext ctx);
	/**
	 * Visit a parse tree produced by {@link CtlCompilerParser#cont}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCont(CtlCompilerParser.ContContext ctx);
}