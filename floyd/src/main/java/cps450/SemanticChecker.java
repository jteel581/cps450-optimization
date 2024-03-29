// SemanticChecker.java
// This file holds the SemanticChecker class and all  it's logic
package cps450;

import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;

import cps450.FloydParser.AdOpTermNybbleContext;
import cps450.FloydParser.AdopExpressionNybbleContext;
import cps450.FloydParser.AndExpressionFactorContext;
import cps450.FloydParser.AndFactoidFactorContext;
import cps450.FloydParser.Argument_declContext;
import cps450.FloydParser.Argument_decl_listContext;
import cps450.FloydParser.Assignment_stmtContext;
import cps450.FloydParser.BbyteFactoidContext;
import cps450.FloydParser.BracketExpressionContext;
import cps450.FloydParser.Call_stmtContext;
import cps450.FloydParser.Class_declContext;
import cps450.FloydParser.ConcatExpressionBbyteContext;
import cps450.FloydParser.ConcatNybbleBbyteContext;
import cps450.FloydParser.ExprTailExpressionContext;
import cps450.FloydParser.ExprTypeContext;
import cps450.FloydParser.ExpressionContext;
import cps450.FloydParser.ExpressionFactoidContext;
import cps450.FloydParser.FactoidFactorContext;
import cps450.FloydParser.FactorExpressionContext;

import cps450.FloydParser.IdTypeContext;
import cps450.FloydParser.If_stmtContext;
import cps450.FloydParser.Loop_stmtContext;
import cps450.FloydParser.Method_declContext;
import cps450.FloydParser.MicrotermTermContext;
import cps450.FloydParser.MulOPExpressionTermContext;
import cps450.FloydParser.MulOpMicrotermTermContext;
import cps450.FloydParser.NanotermContext;
import cps450.FloydParser.NanotermFactoidContext;
import cps450.FloydParser.NanotermMicrotermContext;
import cps450.FloydParser.NewTypeExpressionContext;
import cps450.FloydParser.NormalExpressionContext;
import cps450.FloydParser.NybbleBbyteContext;
import cps450.FloydParser.OrExpressionContext;
import cps450.FloydParser.ParanNanotermContext;
import cps450.FloydParser.ParenthesizedExpressionContext;
import cps450.FloydParser.RegTypeContext;
import cps450.FloydParser.RegularExprTailContext;
import cps450.FloydParser.RegularMicrotermContext;
import cps450.FloydParser.RegularNanotermContext;
import cps450.FloydParser.StartContext;
import cps450.FloydParser.TermNybbleContext;
import cps450.FloydParser.UnaryOpExpressionMicrotermContext;
import cps450.FloydParser.UnaryOpRegularMicrotermContext;
import cps450.FloydParser.Var_declContext;

public class SemanticChecker extends FloydBaseListener {
	Options ops;
	
	SymbolTable st;
	
	SemanticErrorHandler handyMan;
	Integer numErrors = 0;
	String fileName;
	Integer localOffset = -8;
	Integer instanceVarOffset = 8;
	
	// This is a constructor for the SemanticChecker class
	public SemanticChecker(Options ops) {
		super();
		this.ops = ops;
		this.st = SymbolTable.getInstance();
		this.handyMan = new SemanticErrorHandler();
		this.fileName = ops.getFileNames().get(0);
	}
	
	

	@Override
	public void exitArgument_decl(Argument_declContext ctx) {
		String name = ctx.IDENTIFIER().getText();
		String typeStr;
		Type t;
		if (ctx.type().ttype == Type.INT)
		{
			t = Type.INT;
			typeStr = "int";
		}
		else if (ctx.type().ttype == Type.STRING)
		{
			t = Type.STRING;
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			typeStr = "string";
		}
		else if (ctx.type().ttype == Type.BOOLEAN)
		{
			t = Type.BOOLEAN;
			typeStr = "boolean";
		}
		else 
		{
			t = Type.ERROR;
			typeStr = "<error>";
		}
		VarDecl decl = new VarDecl(t, name);
		st.push(name, decl);
		ctx.sym = st.lookup(name);
		
	}



	@Override
	public void exitVar_decl(Var_declContext ctx) {
		String name = ctx.IDENTIFIER().getText();
		String typeStr; 
		Type t;
		if (ctx.type() != null)
		{
			if (ctx.type().ttype == Type.INT)
			{
				ctx.varDeclType = Type.INT;
				t = Type.INT;
				typeStr = "int";
			}
			else if (ctx.type().ttype == Type.STRING)
			{
				ctx.varDeclType = Type.STRING;
				t = Type.STRING;
				handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				typeStr = "string";
			}
			else if (ctx.type().ttype == Type.BOOLEAN)
			{
				ctx.varDeclType = Type.BOOLEAN;
				t = Type.BOOLEAN;
				typeStr = "boolean";
			}
			else 
			{
				
				ctx.varDeclType = Type.getTypeForName(ctx.type().getText());
				t = ctx.varDeclType;
				typeStr = ctx.type().getText();
			}
			VarDecl decl = new VarDecl(t, name);
			if (st.getScope() == 2)
			{
				decl.setOffSet(localOffset);
				localOffset -= 4;
			}
			else if (st.getScope() == 1)
			{
				decl.setOffSet(instanceVarOffset);
				instanceVarOffset += 4;
			}
			
			Symbol s = st.lookup(name);

			if (s == null)
			{
				st.push(name, decl);
			}
			else if (s.getScopeNum() != st.getScope())
			{
				st.push(name, decl);
			}
			else
			{
				handyMan.reportError(fileName, ctx, SemanticError.REDECLAREDVAR);
			}
			s = st.lookup(name);
			ctx.sym = s;
		}
		else if (ctx.ASSIGNMENT() != null)
		{
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

		}
		else 
		{
			handyMan.reportError(fileName, ctx, SemanticError.TYPENOTSPECIFIED);

		}
		
		
	}
	

	@Override
	public void enterAssignment_stmt(Assignment_stmtContext ctx) {
		
		super.enterAssignment_stmt(ctx);
	}



	@Override
	public void exitAssignment_stmt(Assignment_stmtContext ctx) {
		String name = ctx.IDENTIFIER().getText();
		Symbol s = st.lookup(name);
		ctx.sym = s;

		if (s == null)
		{
			handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
		}
		else 
		{
			Type symbolType; 

			if (s.varDecl != null)
			{
				symbolType = s.varDecl.type;
			}
			else if (s.methodDecl != null)
			{
				symbolType = s.methodDecl.type;
			}
			else
			{
				symbolType = s.classDecl.type;
			}
			Type varType = ctx.e2.exprType;
			if ((varType != symbolType) && (symbolType != Type.ERROR))
			{
				if (symbolType == null && varType != null)
				{
					handyMan.reportError(fileName, ctx, SemanticError.TYPEMISMATCH, "null", varType.toString());

				}
				else if (varType == null && symbolType != null)
				{
					handyMan.reportError(fileName, ctx, SemanticError.TYPEMISMATCH, symbolType.toString(), "null");

				}
				else if (varType == null && symbolType == null)
				{
					handyMan.reportError(fileName, ctx, SemanticError.TYPEMISMATCH, "null", "null");

				}
				else if (!varType.name.equals(symbolType.name))
				{
					handyMan.reportError(fileName, ctx, SemanticError.TYPEMISMATCH, symbolType.toString(), varType.toString());

				}
			}
			
		}
	}

	@Override
	public void exitNormalExpression(NormalExpressionContext ctx) {
		if (ctx.IDENTIFIER() != null)
		{
			if (ctx.exprtail() != null && !ctx.exprtail().getText().equals(""))
			{

				String className = ctx.IDENTIFIER().getText();
				String methodBeingCalled = ctx.exprtail().getChild(0).getText();
				Symbol s = st.lookup(className);
				if (s != null)
				{
					if (s.classDecl != null)
					{
						// check for method variables
						ClassDecl cd = s.classDecl;
						boolean methodFound = false;
						Integer locationInMethods = 0;
						for (int i = 0; i < cd.methods.size(); i++)
						{
							MethodDecl md = cd.methods.get(i);
							if (md.methodName.equals(methodBeingCalled))
							{
								
								methodFound = true; 
								locationInMethods = i;
								break;
							}
						}
						if (methodFound == true)
						{
							MethodDecl md = s.classDecl.methods.get(locationInMethods);
							ctx.exprtail().exprTailType = md.returnType;

							ArrayList<String> params = md.parameters;
							ArrayList<Type> paramTypes = md.parameterTypes;
							String child2Str = ctx.exprtail().getChild(2).getText();
							if (!child2Str.equals(")"))
							{
								RegularExprTailContext etc = (RegularExprTailContext) ctx.exprtail().getRuleContext();
								Integer exprsSize = etc.expression_list().exprs.size();
								String exprSizeStr = exprsSize.toString();
								Integer paramsSize = params.size();
								String paramsSizestr = paramsSize.toString();
								ExpressionContext ec = etc.expression_list().exprs.get(0);
								if ((exprsSize > 0 && ec.exprType == null) && paramsSize != 0)
								{
									handyMan.reportError(fileName, etc, SemanticError.WRONGPARAMNUM, paramsSizestr , "0" );

								}
								else if (paramsSize == 0 && ec.exprType != null)
								{
									handyMan.reportError(fileName, etc, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

								}
								else if (paramsSize == 0)
								{
								}
								
								else if( exprsSize != params.size() )
								{
									handyMan.reportError(fileName, etc, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

								}
													
								else 
								{
									checkPassedParams(etc, paramTypes);
								}
							}
							else if (params.size() != 0)
							{
								handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM);
							}
							
						}
						else
						{
							
							handyMan.reportError(fileName, ctx, SemanticError.NOSUCHMETHODINCLASS);
						}
					}
					 
				}
				
			}
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				String name = ctx.IDENTIFIER().getText();
				Symbol s = st.lookup(name);
				if (s == null)
				{
					handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
				}
				else
				{
					Type symbolType = null;
					if (s.varDecl != null)
					{
						symbolType = s.varDecl.type;

					}
					else if (s.methodDecl != null)
					{
						symbolType = s.methodDecl.type;

					}
					else
					{
						symbolType = s.classDecl.type;

					}
					if (symbolType == Type.STRING)
					{
						handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

					}
					ctx.exprType = symbolType;
					ctx.sym = s;

				}
				return;
			}
			
		}
		else if (ctx.STRINGLITERAL() != null )
		{
			ctx.exprType = Type.STRING;	
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			return;
		}
		else if (ctx.INTEGERLITERAL() != null)
		{
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				ctx.exprType = Type.INT;
				return;
			}
			
		}
		else if (ctx.TRUE() != null || ctx.FALSE() != null)
		{
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				ctx.exprType = Type.BOOLEAN;
				return;
			}
			
		}
		else if (ctx.ME() != null)
		{
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				Class_declContext cdx = CodeGen.getClassFromMethod(ctx);
				String className = cdx.id1.getText();
				
				Type t = Type.getTypeForName(className);
				ctx.exprType = t;
				return;
			}
			
		}
		else if (ctx.NULL() != null)
		{
			if (dealWithExprTail(ctx))
			{
				handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			}
			ctx.exprType = Type.NULL;
			return;
		}
		else 
		{
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				ctx.exprType = Type.ERROR;
				return;
			}
			
		}		
		
	}

	@Override
	public void exitUnaryOpRegularMicroterm(UnaryOpRegularMicrotermContext ctx) {
		String op = ctx.unary_op().getText();
		if (ctx.IDENTIFIER() != null)
		{
			String name = ctx.IDENTIFIER().getText();
			Symbol s = st.lookup(name);
			if (s == null)
			{
				handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
			}
			else
			{
				ctx.sym = s;

				Type symbolType;
				if (s.varDecl != null)
				{
					symbolType = s.varDecl.type;
				}
				else if (s.methodDecl != null)
				{
					symbolType = s.methodDecl.returnType;

				}
				else
				{
					symbolType = s.classDecl.type;
					if (ctx.exprtail() != null )
					{
						if(dealWithExprTail(ctx))
						{
							ctx.exprType = ctx.exprtail().exprTailType;
							return;
						}
					}
				}
				if (symbolType == Type.STRING)
				{
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
					

				}
				if ((op.equals("-") || op.equals("+")) && symbolType == Type.INT)
				{
					ctx.exprType = symbolType;

				}
				else if (op.equals("-") || op.equals("+"))
				{
					ctx.exprType = Type.ERROR;
					if (symbolType != Type.ERROR)
					{
						handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int", symbolType.name);

					}
				}
				else if (op.equals("not") && (symbolType == Type.BOOLEAN))
				{

					ctx.exprType = symbolType;
				}
				else 
				{
					
					ctx.exprType = Type.ERROR;
					if (symbolType != Type.ERROR)
					{
						handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "boolean", symbolType.name);

					}
				}
			}
			return;
		}
		else if (ctx.STRINGLITERAL() != null )
		{
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			if (op.equals("-") || op.equals("+"))
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "boolean", "string");
				ctx.exprType = Type.ERROR;
			}
			else
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int", "string");
				ctx.exprType = Type.ERROR;

			}

			return;
		}
		else if (ctx.INTEGERLITERAL() != null)
		{
			if (op.equals("-") || op.equals("+"))
			{
				ctx.exprType = Type.INT;

			}
			else
			{
				ctx.exprType = Type.ERROR;
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int", "boolean");
			}
			return;
		}
		else if (ctx.TRUE() != null || ctx.FALSE() != null)
		{
			if (op.equals("not"))
			{
				ctx.exprType = Type.BOOLEAN;

			}
			else 
			{
				ctx.exprType = Type.ERROR;
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int", "boolean");

			}
			return;
		}
		else if (ctx.ME() != null)
		{
			
			ctx.exprType = Type.ERROR;
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			return;
		}
		else 
		{
			ctx.exprType = Type.ERROR;
			return;
		}
		
	}
	
	

	



	@Override
	public void exitExpressionFactoid(ExpressionFactoidContext ctx) {
		Type typeOne = ctx.nanoterm().nanoTermType;
		Type typeTwo = ctx.expression().exprType;
		String typeOneStr = typeOne.name;
		String typeTwoStr = typeTwo.name;
		String op = ctx.relational_op().getText();
		if (op.equals(">") || op.equals(">="))
		{
			if (typeOneStr.equals(typeTwoStr) && (typeOne == Type.INT || typeOne == Type.STRING))
			{
				if (typeOne == Type.STRING)
				{
					ctx.factoidType = Type.BOOLEAN;
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				}
				else
				{
					ctx.factoidType = Type.BOOLEAN;

				}
			}
			else
			{
				ctx.factoidType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int or string", typeOneStr + " and " + typeTwoStr);

				}

			}
		}
		else
		{
			if (typeOneStr.equals(typeTwoStr) && (typeOneStr.equals("int") || typeOneStr.equals("string") || typeOneStr.equals("boolean")))
			{
				if (typeOne == Type.STRING)
				{
					ctx.factoidType = Type.BOOLEAN;
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

				}
				else
				{
					ctx.factoidType = Type.BOOLEAN;

				}
			}
			else
			{
				ctx.factoidType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int, string, or bool", typeOne.name + " and " + typeTwo.name);

				}

			}
		}
	}



	@Override
	public void exitAdOpTermNybble(AdOpTermNybbleContext ctx) {
		Type nybbleType = ctx.nybble().nybbleType;
		Type termType = ctx.term().termType;
		if (nybbleType != null && termType != null)
		{
			if (nybbleType.name.equals(termType.name) )
			{
				if (nybbleType.name.equals("int"))
				{
					ctx.nybbleType = nybbleType;

				}
				else 
				{
					ctx.nybbleType = Type.ERROR;
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, nybbleType.name);

				}
			}
			else
			{
				ctx.nybbleType = Type.ERROR;
				if (termType != Type.ERROR && nybbleType != Type.ERROR)
				{
					if (!termType.name.equals("int"))
					{
						handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, termType.name);

					}
					else 
					{
						handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, nybbleType.name);

					}

				}
			}
		}
		
	}
	
	

	@Override
	public void exitAdopExpressionNybble(AdopExpressionNybbleContext ctx) {
		Type nybbleType = ctx.nybble().nybbleType;
		Type termType = ctx.expression().exprType;
		if (nybbleType.name.equals(termType.name) && nybbleType.name.equals("int"))
		{
			ctx.nybbleType = nybbleType;
		}
		else
		{
			ctx.nybbleType = Type.ERROR;
			if (termType != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, termType.name);

			}
		}
	}


	@Override
	public void exitMicrotermTerm(MicrotermTermContext ctx) {
		ctx.termType = ctx.microterm().exprType;
	}



	@Override
	public void exitTermNybble(TermNybbleContext ctx) {
		Type termType = ctx.term().termType;
		ctx.nybbleType = termType;
	}

	
	@Override
	public void exitMulOpMicrotermTerm(MulOpMicrotermTermContext ctx) {
		Type typeOne = ctx.microterm().exprType;
		Type typeTwo = ctx.term().termType;
		if (typeOne.name.equals(typeTwo.name) && typeOne.name.equals("int"))
		{
			ctx.termType = typeOne;
		}
		else
		{
			ctx.termType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, typeOne.name + " and " + typeTwo.name);

			}
		}
	}



	@Override
	public void exitMulOPExpressionTerm(MulOPExpressionTermContext ctx) {
		Type typeOne = ctx.expression().exprType;
		Type typeTwo = ctx.term().termType;
		if (typeOne.name.equals(typeTwo.name) && typeOne.name.equals("int"))
		{
			ctx.termType = typeOne;
		}
		else
		{
			ctx.termType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, typeOne.name + " and " + typeTwo.name);

			}
		}
	}



	@Override
	public void exitUnaryOpExpressionMicroterm(UnaryOpExpressionMicrotermContext ctx) {
		String op = ctx.unary_op().getText();
		if (op.equals("not"))
		{
			if (ctx.expression().exprType.name.equals("boolean"))
			{
				ctx.exprType = Type.BOOLEAN;
			}
			else
			{
				ctx.exprType = Type.ERROR;
				if (ctx.expression().exprType != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.BOOLEAN.name, ctx.expression().exprType.name);

				}

			}
		}
		else
		{
			if (ctx.expression().exprType.name.equals("int"))
			{
				ctx.exprType = Type.INT;
			}
			else
			{
				ctx.exprType = Type.ERROR;
				if (ctx.expression().exprType != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE,  Type.INT.name, ctx.expression().exprType.name);

				}

			}
		}
		
	}





	@Override
	public void exitRegularMicroterm(RegularMicrotermContext ctx) {		
		
		if (ctx.IDENTIFIER() != null)
		{
			String name = ctx.IDENTIFIER().getText();
			Symbol s = st.lookup(name);
			if (s == null)
			{
				ctx.exprType = Type.ERROR;
				handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
			}
			else
			{
				ctx.sym = s;

				Type symbolType;
				if (s.varDecl != null)
				{
					symbolType = s.varDecl.type;
				}
				else if (s.methodDecl != null)
				{
					symbolType = s.methodDecl.returnType;

				}
				else
				{
					symbolType = s.classDecl.type;
					if (ctx.exprtail() != null )
					{
						if(dealWithExprTail(ctx))
						{
							ctx.exprType = ctx.exprtail().exprTailType;
							return;
						}
					}
					
				}
				if (symbolType != null && symbolType == Type.STRING)
				{
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

				}
				ctx.exprType = symbolType;
			}
			return;
		}
		else if (ctx.STRINGLITERAL() != null )
		{
			ctx.exprType = Type.STRING;
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			return;
		}
		else if (ctx.INTEGERLITERAL() != null)
		{
			ctx.exprType = Type.INT;
			return;
		}
		else if (ctx.TRUE() != null || ctx.FALSE() != null)
		{
			ctx.exprType = Type.BOOLEAN;
			return;
		}
		else if (ctx.ME() != null)
		{
			
			if (dealWithExprTail(ctx))
			{
				ctx.exprType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				Class_declContext cdx = CodeGen.getClassFromMethod(ctx);
				String className = cdx.id1.getText();
				
				Type t = Type.getTypeForName(className);
				Symbol s = st.lookup(className);
				ctx.exprType = t;
				ctx.sym = s;
				return;
			}
			
		}
		else 
		{
			ctx.exprType = Type.ERROR;
			return;
		}
		
	}
	
	

	



	@Override
	public void exitFactorExpression(FactorExpressionContext ctx) {
		if (dealWithExprTail(ctx))
		{
			ctx.exprType = ctx.exprtail().exprTailType;
		}
		else
		{
			ctx.exprType = ctx.factor().factorType;
		}
	}

	@Override
	public void exitFactoidFactor(FactoidFactorContext ctx) {
		ctx.factorType = ctx.factoid().factoidType;
	}

	@Override
	public void exitBbyteFactoid(BbyteFactoidContext ctx) {
		ctx.factoidType = ctx.bbyte().bbyteType;
	}

	@Override
	public void exitNybbleBbyte(NybbleBbyteContext ctx) {
		ctx.bbyteType = ctx.nybble().nybbleType;
	}



	@Override
	public void exitRegType(RegTypeContext ctx) {
		String typeStr = ctx.getText();
		if (typeStr.equals("int"))
		{
			ctx.ttype = Type.INT;
		}
		else if (typeStr.equals("boolean"))
		{
			ctx.ttype = Type.BOOLEAN;
		}
		else if (typeStr.equals("string"))
		{
			ctx.ttype = Type.STRING;
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

		}
		else
		{
			ctx.ttype = Type.ERROR;
		}
	}



	@Override
	public void exitExprType(ExprTypeContext ctx) {
		ctx.ttype = Type.ERROR;
		handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
		
	}



	@Override
	public void exitIdType(IdTypeContext ctx) {
		Symbol s = st.lookup(ctx.IDENTIFIER().getText());
		if (s == null)
		{
			handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
		}
		else if (s.methodDecl != null)
		{
			ctx.ttype = s.methodDecl.returnType;
		}
		else if (s.classDecl != null)
		{
			ctx.ttype = s.classDecl.type;
		}
		else
		{
			
			ctx.ttype = s.decl.type;
		}
	}


	
	@Override
	public void exitNewTypeExpression(NewTypeExpressionContext ctx) {
			String typeName = ctx.type().getText();
			Type t = Type.getTypeForName(typeName);
			ctx.exprType = t;
			ctx.sym = st.lookup(typeName);
		
		
	}

	


	@Override
	public void exitOrExpression(OrExpressionContext ctx) {
		if (dealWithExprTail(ctx))
		{
			Type typeOne = ctx.expression().exprType;
			Type typeTwo = ctx.exprtail().exprTailType;
			if (typeOne.name.equals(typeTwo.name) && typeOne.name.equals("boolean"))
			{
				ctx.exprType = typeOne;
			}
			else if (typeOne.name.equals("boolean"))
			{
				ctx.exprType = Type.ERROR;
				if (typeTwo != Type.ERROR) 
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, "boolean and " + typeTwo.name);

				}

			}
			else
			{
				ctx.exprType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, typeOne.name + " and " + typeTwo.name);

				}
			}
		}
		else
		{
			Type typeOne = ctx.expression().exprType;
			Type typeTwo = ctx.factor().factorType;
			if (typeOne.name.equals(typeTwo.name) && typeOne.name.equals("boolean"))
			{
				ctx.exprType = typeOne;
			}
			else if (typeOne.name.equals("boolean"))
			{
				ctx.exprType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, "boolean and " + typeTwo.name);

				}

			}
			else
			{
				ctx.exprType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, typeOne.name + " and " + typeTwo.name);

				}
			}
		}
		
		
	}
	
	

	@Override
	public void exitParenthesizedExpression(ParenthesizedExpressionContext ctx) {
		if (dealWithExprTail(ctx))
		{
			ctx.exprType = ctx.exprtail().exprTailType;
		}
		else
		{
			ctx.exprType = ctx.expression().exprType;

		}
	}
	
	
	
	

	


	@Override
	public void exitParanNanoterm(ParanNanotermContext ctx) {
		ctx.nanoTermType = ctx.expression().exprType;
	}



	@Override
	public void exitBracketExpression(BracketExpressionContext ctx) {
		
		ctx.exprType = Type.ERROR;
		handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
	}
	
	

	


	@Override
	public void exitExprTailExpression(ExprTailExpressionContext ctx) {
		ctx.exprType = ctx.exprtail().exprTailType;
		ctx.sym = ctx.exprtail().sym;
	}

	


	@Override
	public void exitRegularExprTail(RegularExprTailContext ctx) {
		String identifier = ctx.IDENTIFIER().getText();
		if(!ctx.getParent().getText().contains("."))
		{
			Symbol s = st.lookup(identifier);
			if ( s != null)
			{
				ctx.sym = s;

				Type t;
				if (s.varDecl != null)
				{
					t = s.varDecl.type;
				}
				else if (s.methodDecl != null)
				{
					t = s.methodDecl.returnType;
					String methodBeingCalled = ctx.IDENTIFIER().getText();
					MethodDecl md = s.methodDecl;
					ArrayList<String> params = md.parameters;
					ArrayList<Type> paramTypes = md.parameterTypes;
					Integer exprsSize = ctx.expression_list().exprs.size();
					String exprSizeStr = exprsSize.toString();
					Integer paramsSize = params.size();
					String paramsSizestr = paramsSize.toString();
					if ((exprsSize > 0 && ctx.expression_list().exprs.get(0).exprType == null) && paramsSize != 0)
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , "0" );

					}
					else if (paramsSize == 0 && ctx.expression_list().exprs.get(0).exprType != null)
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

					}
					else if (paramsSize == 0)
					{
						
					}
					
					else if( exprsSize != params.size() )
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

					}
										
					else 
					{
						checkPassedParams(ctx, paramTypes);
					}
					ctx.sym = s;
					ctx.exprTailType = md.returnType;			
				} 
				else
				{
					t = s.classDecl.type;
				}
				ctx.exprTailType = t;
			}
			else 
			{
				ctx.exprTailType = Type.ERROR;
				handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDMETHOD);
			}
		}
		else
		{
			String className = ctx.getParent().getStart().getText();
			if (className.equals("("))
			{
				className = ctx.getParent().getChild(1).getText();
				className = className.replace("new", "");
			}
			if (className.contains("\""))
			{
				handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				ctx.exprTailType = Type.ERROR;
			}
			else
			{
				String methodName = ctx.IDENTIFIER().getText();
				Symbol s = st.lookup(className);
				ctx.sym = s; 
				if (s == null)
				{
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
					ctx.exprTailType = Type.ERROR;
				}
				else
				{
					ClassDecl cd = s.classDecl;
					if (cd == null)
					{
						handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
						ctx.exprTailType = Type.ERROR;

					}
					else
					{
						MethodDecl md = null;
						if (cd.methods == null)
						{
							handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
							ctx.exprTailType = Type.ERROR;

						}
						else
						{
							for (int i = 0; i < cd.methods.size(); i++)
							{
								MethodDecl mdl = cd.methods.get(i);
								if (mdl.methodName.equals(methodName))
								{
									md = mdl;
									break;
								}
							}
							
							if (md == null)
							{
								handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

							}
							else
							{
								ctx.exprTailType = md.returnType;

							}
						}
						
					}
				}
				
				
			}
			
		}
		
	}



	@Override
	public void exitAndFactoidFactor(AndFactoidFactorContext ctx) {
		Type typeOne = ctx.factor().factorType;
		Type typeTwo = ctx.factoid().factoidType;
		if(typeOne.name.equals(typeTwo.name) && typeOne.name.equals("boolean"))
		{
			ctx.factorType = typeOne;
		}
		else if (typeOne.name.equals("boolean"))
		{
			ctx.factorType = Type.ERROR;
		}
		else
		{
			ctx.factorType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, typeOne.name + " and " + typeTwo.name);

			}
		}
	}



	@Override
	public void exitAndExpressionFactor(AndExpressionFactorContext ctx) {
		Type typeOne = ctx.factor().factorType;
		Type typeTwo = ctx.expression().exprType;
		if(typeOne.name.equals(typeTwo.name) && typeOne.name.equals("boolean"))
		{
			ctx.factorType = typeOne;
		}
		else if (typeOne.name.equals("boolean"))
		{
			ctx.factorType = Type.ERROR;
		}
		else
		{
			ctx.factorType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, Type.BOOLEAN.name, typeOne.name + " and " + typeTwo.name);

			}
		}
	}

	


	@Override
	public void exitNanotermFactoid(NanotermFactoidContext ctx) {
		Type typeOne = ctx.n1.nanoTermType;
		Type typeTwo = ctx.n2.nanoTermType;
		String typeOneStr = typeOne.name;
		String typeTwoStr = typeTwo.name;
		String op = ctx.relational_op().getText();
		if (op.equals(">") || op.equals(">="))
		{
			if (typeOneStr.equals(typeTwoStr) && (typeOneStr.equals("int") || typeOneStr.equals("string")))
			{
				if (typeOne == Type.STRING)
				{
					ctx.factoidType = Type.BOOLEAN;
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				}
				else
				{
					ctx.factoidType = Type.BOOLEAN;

				}
			}
			else
			{
				ctx.factoidType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int or string", typeOneStr + " and " + typeTwoStr);

				}

			}
		}
		else
		{
			if (typeOneStr.equals(typeTwoStr) && (typeOneStr.equals("int") || typeOneStr.equals("string") || typeOneStr.equals("boolean")))
			{
				if (typeOne == Type.STRING)
				{
					ctx.factoidType = Type.BOOLEAN;
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

				}
				else
				{
					ctx.factoidType = Type.BOOLEAN;

				}
			}
			else
			{
				ctx.factoidType = Type.ERROR;
				if (typeTwo != Type.ERROR)
				{
					handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE, "int, string, or bool", typeOneStr + " and " + typeTwoStr);

				}

			}
		}
		
	}
	
	



	



	@Override
	public void exitNanotermMicroterm(NanotermMicrotermContext ctx) {
		ctx.exprType = ctx.nanoterm().nanoTermType;
	}



	



	@Override
	public void exitRegularNanoterm(RegularNanotermContext ctx) {
		if (ctx.IDENTIFIER() != null)
		{
			String name = ctx.IDENTIFIER().getText();
			Symbol s = st.lookup(name);
			if (s == null)
			{
				ctx.nanoTermType = Type.ERROR;
				handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDVAR);
			}
			else
			{
				ctx.sym = s;

				Type symbolType;
				if (s.varDecl != null)
				{
					symbolType = s.varDecl.type;
				}
				else if (s.methodDecl != null)
				{
					symbolType = s.methodDecl.returnType;

				}
				else
				{
					symbolType = s.classDecl.type;
					if (ctx.exprtail() != null )
					{
						if(dealWithExprTail(ctx))
						{
							ctx.nanoTermType = ctx.exprtail().exprTailType;
							return;
						}
					}
				}
				if (symbolType != null && symbolType == Type.STRING)
				{
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);

				}
				ctx.nanoTermType = symbolType;
			}
			return;
		}
		else if (ctx.STRINGLITERAL() != null )
		{
			ctx.nanoTermType = Type.STRING;
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			return;
		}
		else if (ctx.INTEGERLITERAL() != null)
		{
			ctx.nanoTermType = Type.INT;
			return;
		}
		else if (ctx.TRUE() != null || ctx.FALSE() != null)
		{
			ctx.nanoTermType = Type.BOOLEAN;
			return;
		}
		else if (ctx.ME() != null)
		{
			if (dealWithExprTail(ctx))
			{
				ctx.nanoTermType = ctx.exprtail().exprTailType;
				return;
			}
			else
			{
				Class_declContext cdx = CodeGen.getClassFromMethod(ctx);
				String className = cdx.id1.getText();
				
				Type t = Type.getTypeForName(className);
				Symbol s = st.lookup(className);
				ctx.nanoTermType = t;
				ctx.sym = s;
				return;
			}
		}
		else 
		{
			ctx.nanoTermType = Type.ERROR;
			return;
		}
	}



	



	



	@Override
	public void exitConcatNybbleBbyte(ConcatNybbleBbyteContext ctx) {
		Type typeOne = ctx.bbyte().bbyteType;
		Type typeTwo = ctx.nybble().nybbleType;
		if (typeOne.name.equals(typeTwo.name) && typeOne.name == "string")
		{
			ctx.bbyteType = typeOne;
		}
		else
		{
			ctx.bbyteType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE ,"strings", typeOne.name + " and " + typeTwo.name);

			}

		}
	}

	


	@Override
	public void exitConcatExpressionBbyte(ConcatExpressionBbyteContext ctx) {
		Type typeOne = ctx.bbyte().bbyteType;
		Type typeTwo = ctx.expression().exprType;
		if (typeOne.name.equals(typeTwo.name) && typeOne.name == "string")
		{
			ctx.bbyteType = typeOne;
		}
		else
		{
			ctx.bbyteType = Type.ERROR;
			if (typeTwo != Type.ERROR)
			{
				handyMan.reportError(fileName, ctx, SemanticError.INCORRECTTYPE ,"strings", typeOne.name + " and " + typeTwo.name);

			}

		}
	}

	
	


	@Override
	public void exitMethod_decl(Method_declContext ctx) throws LessThanZeroException {

		st.endScope();
		localOffset = -8;
		
	}


	@Override
	public void enterClass_decl(Class_declContext ctx) {
		Symbol s = new Symbol();
		s.setName(ctx.id1.getText());
		ClassDecl cd = new ClassDecl(ctx.id1.getText(), null, null);
		st.push(s.getName(), cd);
		Type.createType(cd);
		st.beginScope();
	}
	
	



	@Override
	public void enterStart(StartContext ctx) {
		ArrayList<String> params = new ArrayList<>();
		ArrayList<Type> paramTypes = new ArrayList<>();
		ArrayList<String> params2 = new ArrayList<>();
		ArrayList<Type> paramTypes2 = new ArrayList<>();
		ArrayList<VarDecl> inVars = new ArrayList<>();
		ArrayList<MethodDecl> inMethods = new ArrayList<>();
		ArrayList<VarDecl> outVars = new ArrayList<>();
		ArrayList<MethodDecl> outMethods = new ArrayList<>();
		MethodDecl readIntDecl = new MethodDecl(Type.INT, params, paramTypes );
		readIntDecl.methodName = "readint";
		readIntDecl.returnType = Type.INT;
		inMethods.add(readIntDecl);
		ClassDecl inClassDecl = new ClassDecl("in", inVars, inMethods);
		st.push("in", inClassDecl);
		params2.add("num");
		paramTypes2.add(Type.INT);
		MethodDecl writeIntDecl = new MethodDecl(Type.VOID, params2, paramTypes2);
		writeIntDecl.methodName = "writeint";
		outMethods.add(writeIntDecl);
		ClassDecl outClassDecl = new ClassDecl("out", outVars, outMethods);
		st.push("out", outClassDecl);

	}



	@Override
	public void exitStart(StartContext ctx) {
		Integer classNum = ctx.classes.size();
		Class_declContext cd = ctx.classes.get(classNum - 1);
		Boolean containsStart = false;
		for (Method_declContext md : cd.methods)
		{
			if (md.id1.getText().equals("start"))
			{
				containsStart = true;
				break;
			}
		}
		if (!containsStart)
		{
			handyMan.reportError(fileName, ctx, SemanticError.NOSTARTMETHOD);
		}
		numErrors = handyMan.numErrors;
	}



	@Override
	public void enterMethod_decl(Method_declContext ctx) {
		ArrayList<Type> paramTypes = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();
		Type type = null;
		if ( ctx.type() != null)
		{
			String typeStr = ctx.type().getText();
			if (typeStr.equals("string"))
			{
				type = Type.STRING;
				handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
			}
			else if (typeStr.equals("int"))
			{
				type = Type.INT;
			}
			else if (typeStr.equals("boolean"))
			{
				type = Type.BOOLEAN;
			}
			else if (typeStr.equals("void"))
			{
				type = Type.VOID;
			}
			else
			{
				type = new Type(typeStr);
			}

		}
		else 
		{
			type = Type.VOID;
		}
		String name = ctx.id1.getText();
		
		Symbol lookedUpSymbol = st.lookup(name);
		if (lookedUpSymbol != null && (lookedUpSymbol.scopeNum == st.getScope()))
		{
			handyMan.reportError(fileName, ctx, SemanticError.REDECLAREDMETHOD);

		}
		else if (type == null)
		{
			ctx.methodType = Type.VOID;
			if (ctx.argument_decl_list() != null)
			{
				getParams(ctx, params, paramTypes);

			}		
		}
		else 
		{
			ctx.methodType = type;
			if (ctx.argument_decl_list() != null)
			{
				getParams(ctx, params, paramTypes);
			}
			
		}
		MethodDecl md = new MethodDecl(type, params, paramTypes);
		md.methodName = name;
		Symbol s = new Symbol();
		s.setName(name);
		
		s.methodDecl = md;
		ctx.sym = s;
		st.push(name, s.methodDecl);
		st.beginScope();
	}


	
	

	@Override
	public void exitArgument_decl_list(Argument_decl_listContext ctx) {
		Integer offSet = 12;
		for (Argument_declContext adc : ctx.args)
		{
			Symbol s = st.lookup(adc.IDENTIFIER().getText());	
			VarDecl vd = s.varDecl;
			vd.setOffSet(offSet);
			offSet += 4;
			
		}
	}



	@Override
	public void exitClass_decl(Class_declContext ctx) {
		String identifierOne = ctx.id1.getText();
		String identifierTwo = "";
		String identifierThree = ctx.id3.getText();

		if (ctx.id2 != null)
		{
			identifierTwo = ctx.id2.getText();
		}
		
		if (identifierTwo != "")
		{
			handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
		}
		
		String className = ctx.id1.getText();
		ctx.classType = new Type(className);
		String superClassName = "";
		ArrayList<VarDecl> variables = new ArrayList<>();
	    ArrayList<MethodDecl> methods = new ArrayList<>();
		for (Var_declContext vdc : ctx.vars)
		{
			if (vdc.type() != null)
			{
				String idStr = vdc.IDENTIFIER().getText();
				Type t = vdc.type().ttype;
				VarDecl v = new VarDecl( t, idStr);
				if (vdc.ASSIGNMENT() != null)
				{
					handyMan.reportError(fileName, vdc.ASSIGNMENT());
				}
				variables.add(v);
			}
			
		}
		for (Method_declContext mdc : ctx.methods)
		{
			ArrayList<String> paramNames = new ArrayList<>();
			ArrayList<Type> paramTypes = new ArrayList<>();
			if (mdc.argument_decl_list() != null)
			{
				getParams(mdc, paramNames, paramTypes);
			}
			MethodDecl m = new MethodDecl(mdc.methodType, paramNames, paramTypes);
			m.methodName = mdc.id1.getText();
			methods.add(m);
		}
		ctx.varList = variables;
		ctx.methodList = methods;
		ClassDecl cd = new ClassDecl(className, superClassName, variables, methods);
		Symbol s = st.lookup(className);
		s.setName(className);
		s.classDecl = cd;
		st.endScope();
		instanceVarOffset = 8;
		st.push(className, s.classDecl);
	}
	
	
	
	@Override
	public void exitIf_stmt(If_stmtContext ctx) {
		Type t = ctx.expression().exprType;
		if (!t.name.equals("boolean"))
		{
			handyMan.reportError(fileName, ctx, SemanticError.LOOPTESTNOTBOOL);
		}
	}



	@Override
	public void exitLoop_stmt(Loop_stmtContext ctx) {
		Type t = ctx.expression().exprType;
		if (!t.name.equals("boolean"))
		{
			handyMan.reportError(fileName, ctx, SemanticError.LOOPTESTNOTBOOL);
		}
	}



	@Override
	public void exitCall_stmt(Call_stmtContext ctx) {
		if (ctx.expression() == null)
		{
			String methodBeingCalled = ctx.IDENTIFIER().getText();
			Symbol s = st.lookup(methodBeingCalled);
			if (s != null)
			{
				if (s.methodDecl != null)
				{
					// check for method variables
					MethodDecl md = s.methodDecl;
					ArrayList<String> params = md.parameters;
					ArrayList<Type> paramTypes = md.parameterTypes;
					Integer exprsSize = ctx.expression_list().exprs.size();
					String exprSizeStr = exprsSize.toString();
					Integer paramsSize = params.size();
					String paramsSizestr = paramsSize.toString();
					if ((exprsSize > 0 && ctx.expression_list().exprs.get(0).exprType == null) && paramsSize != 0)
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , "0" );

					}
					else if (paramsSize == 0 && ctx.expression_list().exprs.get(0).exprType != null)
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

					}
					else if (paramsSize == 0)
					{
						
					}
					
					else if( exprsSize != params.size() )
					{
						handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

					}
										
					else 
					{
						checkPassedParams(ctx, paramTypes);
					}					
				}
				else
				{
					
					handyMan.reportError(fileName, ctx, SemanticError.UNDECLAREDMETHOD);
				}
			}
			else
			{
				handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				
			}
			
		}
		else
		{
			String className = ctx.expression().getText();
			String methodBeingCalled = ctx.IDENTIFIER().getText();
			Symbol s = st.lookup(className);
			if (s != null)
			{
				ctx.sym = s;

				if (s.classDecl != null)
				{
					// check for method variables
					ClassDecl cd = s.classDecl;
					boolean methodFound = false;
					Integer locationInMethods = 0;
					for (int i = 0; i < cd.methods.size(); i++)
					{
						MethodDecl md = cd.methods.get(i);
						if (md.methodName.equals(methodBeingCalled))
						{
							
							methodFound = true; 
							locationInMethods = i;
							break;
						}
					}
					if (methodFound == true)
					{
						MethodDecl md = s.classDecl.methods.get(locationInMethods);
						ArrayList<String> params = md.parameters;
						ArrayList<Type> paramTypes = md.parameterTypes;
						Integer exprsSize = ctx.expression_list().exprs.size();
						String exprSizeStr = exprsSize.toString();
						Integer paramsSize = params.size();
						String paramsSizestr = paramsSize.toString();
						if ((exprsSize > 0 && ctx.expression_list().exprs.get(0).exprType == null) && paramsSize != 0)
						{
							handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , "0" );

						}
						else if (paramsSize == 0 && ctx.expression_list().exprs.get(0).exprType != null)
						{
							handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

						}
						else if (paramsSize == 0)
						{
							
						}
						
						else if( exprsSize != params.size() )
						{
							handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMNUM, paramsSizestr , exprSizeStr );

						}
											
						else 
						{
							checkPassedParams(ctx, paramTypes);
						}
					}
					else
					{
						
						handyMan.reportError(fileName, ctx, SemanticError.NOSUCHMETHODINCLASS);
					}
				}
				 
			}
			
			
		}
	}
	
	
	// This method checks the parameters passed to a method to see if they are the right type
	public boolean checkPassedParams(Call_stmtContext ctx, ArrayList<Type> typeList)
	{
		boolean correctParams = false;
		String gottenTypes = "";
		for (int i = 0; i < ctx.expression_list().exprs.size(); i++)
		{
			ExpressionContext ec = ctx.expression_list().exprs.get(i);

			if (!typeList.get(i).name.equals(ec.exprType.name))
			{
				correctParams = false;
				break;
			}
			else
			{
				correctParams = true;

			}
			
			
			
		}
		if (!correctParams)
		{
			String correctTypes = "";
			for (int i = 0; i < typeList.size(); i++)
			{
				correctTypes = correctTypes + typeList.get(i).name;
				if (i != typeList.size() - 1)
				{
					correctTypes = correctTypes + ", ";
				}
			}
			for (int i = 0; i < ctx.expression_list().exprs.size(); i++)
			{
				ExpressionContext ec = ctx.expression_list().exprs.get(i);
				gottenTypes = gottenTypes + ec.exprType.name;
				if (i != ctx.expression_list().exprs.size() - 1)
				{
					gottenTypes = gottenTypes + ", ";
				}
			}
			handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMTYPE, correctTypes, gottenTypes);
			return false;
		}
		else
		{
			return true;
		}
		
	}


	// This one also checks the parameters passed to a method to see if they are the correct type.
	public boolean checkPassedParams(RegularExprTailContext ctx, ArrayList<Type> typeList)
	{
		boolean correctParams = false;
		String gottenTypes = "";
		for (int i = 0; i < ctx.expression_list().exprs.size(); i++)
		{
			ExpressionContext ec = ctx.expression_list().exprs.get(i);

			if (!typeList.get(i).name.equals(ec.exprType.name))
			{
				correctParams = false;
				break;
			}
			else
			{
				correctParams = true;

			}
			
			
			
		}
		if (!correctParams)
		{
			String correctTypes = "";
			for (int i = 0; i < typeList.size(); i++)
			{
				correctTypes = correctTypes + typeList.get(i).name;
				if (i != typeList.size() - 1)
				{
					correctTypes = correctTypes + ", ";
				}
			}
			for (int i = 0; i < ctx.expression_list().exprs.size(); i++)
			{
				ExpressionContext ec = ctx.expression_list().exprs.get(i);
				gottenTypes = gottenTypes + ec.exprType.name;
				if (i != ctx.expression_list().exprs.size() - 1)
				{
					gottenTypes = gottenTypes + ", ";
				}
			}
			handyMan.reportError(fileName, ctx, SemanticError.WRONGPARAMTYPE, correctTypes, gottenTypes);
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	// This method gets the parameters from a method declaration and puts them in array lists so that they are easier to process later.
	public void getParams(Method_declContext ctx, ArrayList<String> paramsList, ArrayList<Type> typeList)
	{
		if (ctx.argument_decl_list() != null)
		{
			for (int i = 0; i < ctx.argument_decl_list().args.size(); i++)
			{
				Argument_declContext c = ctx.argument_decl_list().args.get(i);
				String id = c.IDENTIFIER().getText();
				String typeStr = c.type().getText();
				Type type = null;

				if (typeStr.equals("string"))
				{
					type = Type.STRING;
					handyMan.reportError(fileName, ctx, SemanticError.UNSUPPORTED);
				}
				else if (typeStr.equals("int"))
				{
					type = Type.INT;
				}
				else if (typeStr.equals("boolean"))
				{
					type = Type.BOOLEAN;
				}
				else
				{
					type = new Type(typeStr);
				}
				typeList.add(type);
				paramsList.add(id);		
				
			}
		}
	}
	
	// These methods checks to see if there is an ExprTail, and if there is it returns its type
	private boolean dealWithExprTail(RegularMicrotermContext ctx) {
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
		
	}
	
	private boolean dealWithExprTail(RegularNanotermContext ctx) {
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.nanoTermType = t;
			return true;
		}
	}
	
	private boolean dealWithExprTail(UnaryOpRegularMicrotermContext ctx) {
			Type t = ctx.exprtail().exprTailType;
			if (t == null)
			{
				return false;
			}
			else
			{
				ctx.exprType = t;
				return true;
			}
	}
	



	public boolean dealWithExprTail(NormalExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}
	
	public boolean dealWithExprTail(NewTypeExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}
	
	public boolean dealWithExprTail(OrExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}

	public boolean dealWithExprTail(FactorExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}
	
	public boolean dealWithExprTail(ParenthesizedExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}

	public boolean dealWithExprTail(BracketExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}
	
	public boolean dealWithExprTail(ExprTailExpressionContext ctx)
	{
		Type t = ctx.exprtail().exprTailType;
		if (t == null)
		{
			return false;
		}
		else
		{
			ctx.exprType = t;
			return true;
		}
	}
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	

}
