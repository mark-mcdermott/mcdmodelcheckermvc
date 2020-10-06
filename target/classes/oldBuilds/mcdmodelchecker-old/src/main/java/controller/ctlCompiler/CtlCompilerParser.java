// Generated from /Users/markmcdermott/Desktop/mcdmodelchecker/src/main/antlr4/controller/ctlCompiler/CtlCompiler.g4 by ANTLR 4.8

    package controller.ctlCompiler;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CtlCompilerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		LABEL=25;
	public static final int
		RULE_formula = 0, RULE_not = 1, RULE_conj = 2, RULE_and = 3, RULE_or = 4, 
		RULE_imp = 5, RULE_temp = 6, RULE_ax = 7, RULE_ex = 8, RULE_af = 9, RULE_ef = 10, 
		RULE_ag = 11, RULE_eg = 12, RULE_au = 13, RULE_eu = 14, RULE_atom = 15, 
		RULE_bool = 16, RULE_taut = 17, RULE_cont = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"formula", "not", "conj", "and", "or", "imp", "temp", "ax", "ex", "af", 
			"ef", "ag", "eg", "au", "eu", "atom", "bool", "taut", "cont"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'\u00AC'", "'\u2227('", "','", "')'", "'\u2228('", "'\u2192('", 
			"'AX('", "'AX'", "'EX('", "'EX'", "'AF('", "'AF'", "'EF('", "'EF'", "'AG('", 
			"'AG'", "'EG('", "'EG'", "'A['", "'U'", "']'", "'E['", "'\u22A4'", "'\u22A5'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "LABEL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CtlCompiler.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CtlCompilerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FormulaContext extends ParserRuleContext {
		public NotContext not() {
			return getRuleContext(NotContext.class,0);
		}
		public ConjContext conj() {
			return getRuleContext(ConjContext.class,0);
		}
		public ImpContext imp() {
			return getRuleContext(ImpContext.class,0);
		}
		public TempContext temp() {
			return getRuleContext(TempContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitFormula(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_formula);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				not();
				}
				break;
			case T__1:
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				conj();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(40);
				imp();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
			case T__18:
			case T__21:
				enterOuterAlt(_localctx, 4);
				{
				setState(41);
				temp();
				}
				break;
			case T__22:
			case T__23:
				enterOuterAlt(_localctx, 5);
				{
				setState(42);
				bool();
				}
				break;
			case LABEL:
				enterOuterAlt(_localctx, 6);
				{
				setState(43);
				atom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public NotContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_not; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitNot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotContext not() throws RecognitionException {
		NotContext _localctx = new NotContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_not);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(T__0);
			setState(47);
			((NotContext)_localctx).Φ1 = formula();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjContext extends ParserRuleContext {
		public AndContext and() {
			return getRuleContext(AndContext.class,0);
		}
		public OrContext or() {
			return getRuleContext(OrContext.class,0);
		}
		public ConjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterConj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitConj(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitConj(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjContext conj() throws RecognitionException {
		ConjContext _localctx = new ConjContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_conj);
		try {
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				and();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				or();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext Φ2;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public AndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(T__1);
			setState(54);
			((AndContext)_localctx).Φ1 = formula();
			setState(55);
			match(T__2);
			setState(56);
			((AndContext)_localctx).Φ2 = formula();
			setState(57);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext Φ2;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public OrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(T__4);
			setState(60);
			((OrContext)_localctx).Φ1 = formula();
			setState(61);
			match(T__2);
			setState(62);
			((OrContext)_localctx).Φ2 = formula();
			setState(63);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImpContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext Φ2;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public ImpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_imp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterImp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitImp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitImp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImpContext imp() throws RecognitionException {
		ImpContext _localctx = new ImpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_imp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(T__5);
			setState(66);
			((ImpContext)_localctx).Φ1 = formula();
			setState(67);
			match(T__2);
			setState(68);
			((ImpContext)_localctx).Φ2 = formula();
			setState(69);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TempContext extends ParserRuleContext {
		public AxContext ax() {
			return getRuleContext(AxContext.class,0);
		}
		public ExContext ex() {
			return getRuleContext(ExContext.class,0);
		}
		public AfContext af() {
			return getRuleContext(AfContext.class,0);
		}
		public EfContext ef() {
			return getRuleContext(EfContext.class,0);
		}
		public AgContext ag() {
			return getRuleContext(AgContext.class,0);
		}
		public EgContext eg() {
			return getRuleContext(EgContext.class,0);
		}
		public AuContext au() {
			return getRuleContext(AuContext.class,0);
		}
		public EuContext eu() {
			return getRuleContext(EuContext.class,0);
		}
		public TempContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_temp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitTemp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitTemp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TempContext temp() throws RecognitionException {
		TempContext _localctx = new TempContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_temp);
		try {
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				ax();
				}
				break;
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				ex();
				}
				break;
			case T__10:
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(73);
				af();
				}
				break;
			case T__12:
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(74);
				ef();
				}
				break;
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 5);
				{
				setState(75);
				ag();
				}
				break;
			case T__16:
			case T__17:
				enterOuterAlt(_localctx, 6);
				{
				setState(76);
				eg();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 7);
				{
				setState(77);
				au();
				}
				break;
			case T__21:
				enterOuterAlt(_localctx, 8);
				{
				setState(78);
				eu();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AxContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public AxContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ax; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAx(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAx(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAx(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AxContext ax() throws RecognitionException {
		AxContext _localctx = new AxContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ax);
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				match(T__6);
				setState(82);
				((AxContext)_localctx).Φ1 = formula();
				setState(83);
				match(T__3);
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(T__7);
				setState(86);
				((AxContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public ExContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterEx(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitEx(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitEx(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExContext ex() throws RecognitionException {
		ExContext _localctx = new ExContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ex);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				match(T__8);
				setState(90);
				((ExContext)_localctx).Φ1 = formula();
				setState(91);
				match(T__3);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				match(T__9);
				setState(94);
				((ExContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AfContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public AfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_af; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AfContext af() throws RecognitionException {
		AfContext _localctx = new AfContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_af);
		try {
			setState(103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__10:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				match(T__10);
				setState(98);
				((AfContext)_localctx).Φ1 = formula();
				setState(99);
				match(T__3);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(101);
				match(T__11);
				setState(102);
				((AfContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EfContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public EfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterEf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitEf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitEf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EfContext ef() throws RecognitionException {
		EfContext _localctx = new EfContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ef);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				match(T__12);
				setState(106);
				((EfContext)_localctx).Φ1 = formula();
				setState(107);
				match(T__3);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				match(T__13);
				setState(110);
				((EfContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AgContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public AgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgContext ag() throws RecognitionException {
		AgContext _localctx = new AgContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ag);
		try {
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				match(T__14);
				setState(114);
				((AgContext)_localctx).Φ1 = formula();
				setState(115);
				match(T__3);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				match(T__15);
				setState(118);
				((AgContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EgContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public EgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterEg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitEg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitEg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EgContext eg() throws RecognitionException {
		EgContext _localctx = new EgContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_eg);
		try {
			setState(127);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				match(T__16);
				setState(122);
				((EgContext)_localctx).Φ1 = formula();
				setState(123);
				match(T__3);
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(T__17);
				setState(126);
				((EgContext)_localctx).Φ1 = formula();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AuContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext Φ2;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public AuContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_au; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAu(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAu(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAu(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AuContext au() throws RecognitionException {
		AuContext _localctx = new AuContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_au);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(T__18);
			setState(130);
			((AuContext)_localctx).Φ1 = formula();
			setState(131);
			match(T__19);
			setState(132);
			((AuContext)_localctx).Φ2 = formula();
			setState(133);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EuContext extends ParserRuleContext {
		public FormulaContext Φ1;
		public FormulaContext Φ2;
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public EuContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eu; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterEu(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitEu(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitEu(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EuContext eu() throws RecognitionException {
		EuContext _localctx = new EuContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_eu);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(T__21);
			setState(136);
			((EuContext)_localctx).Φ1 = formula();
			setState(137);
			match(T__19);
			setState(138);
			((EuContext)_localctx).Φ2 = formula();
			setState(139);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode LABEL() { return getToken(CtlCompilerParser.LABEL, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_atom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(LABEL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolContext extends ParserRuleContext {
		public TautContext taut() {
			return getRuleContext(TautContext.class,0);
		}
		public ContContext cont() {
			return getRuleContext(ContContext.class,0);
		}
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_bool);
		try {
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				taut();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				cont();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TautContext extends ParserRuleContext {
		public TautContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_taut; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterTaut(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitTaut(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitTaut(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TautContext taut() throws RecognitionException {
		TautContext _localctx = new TautContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_taut);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__22);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContContext extends ParserRuleContext {
		public ContContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cont; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).enterCont(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CtlCompilerListener ) ((CtlCompilerListener)listener).exitCont(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CtlCompilerVisitor ) return ((CtlCompilerVisitor<? extends T>)visitor).visitCont(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContContext cont() throws RecognitionException {
		ContContext _localctx = new ContContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_cont);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(T__23);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33\u009a\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\3\2\5\2/\n\2\3\3\3\3\3\3\3\4"+
		"\3\4\5\4\66\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bR\n\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\5\tZ\n\t\3\n\3\n\3\n\3\n\3\n\3\n\5\nb\n\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\5\13j\n\13\3\f\3\f\3\f\3\f\3\f\3\f\5\fr\n\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\5\rz\n\r\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0082\n\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\5\22\u0094\n\22\3\23\3\23\3\24\3\24\3\24\2\2\25\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"$&\2\2\2\u009a\2.\3\2\2\2\4\60\3\2\2\2\6"+
		"\65\3\2\2\2\b\67\3\2\2\2\n=\3\2\2\2\fC\3\2\2\2\16Q\3\2\2\2\20Y\3\2\2\2"+
		"\22a\3\2\2\2\24i\3\2\2\2\26q\3\2\2\2\30y\3\2\2\2\32\u0081\3\2\2\2\34\u0083"+
		"\3\2\2\2\36\u0089\3\2\2\2 \u008f\3\2\2\2\"\u0093\3\2\2\2$\u0095\3\2\2"+
		"\2&\u0097\3\2\2\2(/\5\4\3\2)/\5\6\4\2*/\5\f\7\2+/\5\16\b\2,/\5\"\22\2"+
		"-/\5 \21\2.(\3\2\2\2.)\3\2\2\2.*\3\2\2\2.+\3\2\2\2.,\3\2\2\2.-\3\2\2\2"+
		"/\3\3\2\2\2\60\61\7\3\2\2\61\62\5\2\2\2\62\5\3\2\2\2\63\66\5\b\5\2\64"+
		"\66\5\n\6\2\65\63\3\2\2\2\65\64\3\2\2\2\66\7\3\2\2\2\678\7\4\2\289\5\2"+
		"\2\29:\7\5\2\2:;\5\2\2\2;<\7\6\2\2<\t\3\2\2\2=>\7\7\2\2>?\5\2\2\2?@\7"+
		"\5\2\2@A\5\2\2\2AB\7\6\2\2B\13\3\2\2\2CD\7\b\2\2DE\5\2\2\2EF\7\5\2\2F"+
		"G\5\2\2\2GH\7\6\2\2H\r\3\2\2\2IR\5\20\t\2JR\5\22\n\2KR\5\24\13\2LR\5\26"+
		"\f\2MR\5\30\r\2NR\5\32\16\2OR\5\34\17\2PR\5\36\20\2QI\3\2\2\2QJ\3\2\2"+
		"\2QK\3\2\2\2QL\3\2\2\2QM\3\2\2\2QN\3\2\2\2QO\3\2\2\2QP\3\2\2\2R\17\3\2"+
		"\2\2ST\7\t\2\2TU\5\2\2\2UV\7\6\2\2VZ\3\2\2\2WX\7\n\2\2XZ\5\2\2\2YS\3\2"+
		"\2\2YW\3\2\2\2Z\21\3\2\2\2[\\\7\13\2\2\\]\5\2\2\2]^\7\6\2\2^b\3\2\2\2"+
		"_`\7\f\2\2`b\5\2\2\2a[\3\2\2\2a_\3\2\2\2b\23\3\2\2\2cd\7\r\2\2de\5\2\2"+
		"\2ef\7\6\2\2fj\3\2\2\2gh\7\16\2\2hj\5\2\2\2ic\3\2\2\2ig\3\2\2\2j\25\3"+
		"\2\2\2kl\7\17\2\2lm\5\2\2\2mn\7\6\2\2nr\3\2\2\2op\7\20\2\2pr\5\2\2\2q"+
		"k\3\2\2\2qo\3\2\2\2r\27\3\2\2\2st\7\21\2\2tu\5\2\2\2uv\7\6\2\2vz\3\2\2"+
		"\2wx\7\22\2\2xz\5\2\2\2ys\3\2\2\2yw\3\2\2\2z\31\3\2\2\2{|\7\23\2\2|}\5"+
		"\2\2\2}~\7\6\2\2~\u0082\3\2\2\2\177\u0080\7\24\2\2\u0080\u0082\5\2\2\2"+
		"\u0081{\3\2\2\2\u0081\177\3\2\2\2\u0082\33\3\2\2\2\u0083\u0084\7\25\2"+
		"\2\u0084\u0085\5\2\2\2\u0085\u0086\7\26\2\2\u0086\u0087\5\2\2\2\u0087"+
		"\u0088\7\27\2\2\u0088\35\3\2\2\2\u0089\u008a\7\30\2\2\u008a\u008b\5\2"+
		"\2\2\u008b\u008c\7\26\2\2\u008c\u008d\5\2\2\2\u008d\u008e\7\27\2\2\u008e"+
		"\37\3\2\2\2\u008f\u0090\7\33\2\2\u0090!\3\2\2\2\u0091\u0094\5$\23\2\u0092"+
		"\u0094\5&\24\2\u0093\u0091\3\2\2\2\u0093\u0092\3\2\2\2\u0094#\3\2\2\2"+
		"\u0095\u0096\7\31\2\2\u0096%\3\2\2\2\u0097\u0098\7\32\2\2\u0098\'\3\2"+
		"\2\2\f.\65QYaiqy\u0081\u0093";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}