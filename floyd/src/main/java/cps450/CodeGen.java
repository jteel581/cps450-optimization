package cps450;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import cps450.FloydParser.Call_stmtContext;
import cps450.FloydParser.Class_declContext;
import cps450.FloydParser.ConcatNybbleBbyteContext;
import cps450.FloydParser.ExprTailExpressionContext;
import cps450.FloydParser.ExpressionContext;
import cps450.FloydParser.ExpressionFactoidContext;
import cps450.FloydParser.FactoidFactorContext;
import cps450.FloydParser.FactorExpressionContext;
import cps450.FloydParser.If_stmtContext;
import cps450.FloydParser.Loop_stmtContext;
import cps450.FloydParser.Method_declContext;
import cps450.FloydParser.MicrotermTermContext;
import cps450.FloydParser.MulOPExpressionTermContext;
import cps450.FloydParser.MulOpMicrotermTermContext;
import cps450.FloydParser.NanotermFactoidContext;
import cps450.FloydParser.NanotermMicrotermContext;
import cps450.FloydParser.NewTypeExpressionContext;
import cps450.FloydParser.NormalExpressionContext;
import cps450.FloydParser.NybbleBbyteContext;
import cps450.FloydParser.OrExpressionContext;
import cps450.FloydParser.ParanNanotermContext;
import cps450.FloydParser.ParenthesizedExpressionContext;
import cps450.FloydParser.RegularExprTailContext;
import cps450.FloydParser.RegularMicrotermContext;
import cps450.FloydParser.RegularNanotermContext;
import cps450.FloydParser.StatementContext;
import cps450.FloydParser.UnaryOpExpressionMicrotermContext;
import cps450.FloydParser.UnaryOpRegularMicrotermContext;
import cps450.FloydParser.Var_declContext;

public class CodeGen extends FloydBaseVisitor<Double> {
	
	
	
	public CodeGen(Options ops) {
		//super();
		this.ops = ops;
	}


	Integer GLOBAL = 0;
	Integer CLASS = 1;
	Integer LOCAL = 2;
	String lastClass = "";
	Class_declContext cdx = null;
	Integer notNum = 0;
	Options ops;

	ArrayList<TargetInstruction> instructions = new ArrayList<>();
	
	
	
	
	public void outputAssembly() throws IOException
	{
		boolean firstData = true;
		boolean firstText = true;
		//String fileName = ops.getFileNames().get(0);
		String fileName = ops.getFileNames().get(0);
		fileName = fileName.replace(".floyd", ".s");
		File assemblyFile = new File(fileName);
		if(!assemblyFile.exists())
		{
			assemblyFile.createNewFile();

		}
		
		PrintWriter pw = new PrintWriter(fileName);
		for (TargetInstruction ti : instructions)
		{
			// printing right now, will send to file later
			// comment
			if (ti.comment != null)
			{
				pw.print(ti.comment);
				//System.out.print(ti.comment);
			}
			// code
			else
			{
				// checking if .data has been printed yet, if not, we print
				if (firstData && ti.directive.equals(".data"))
				{
					pw.println(".data");
					//System.out.println(".data");
					firstData = false;
				}
				// if data is done, we print .text and begin printing that code
				else if (firstText && ti.directive.equals(".text"))
				{
					pw.println(".text");
					
					pw.println(".global main");
						
					
					
					/*
					System.out.println(".text");
					System.out.println(".global main");
					System.out.println("main:");*/
					firstText = false;
				}
				if (ti.instruction != null && ti.label != null)
				{
					pw.println(ti.instruction + " " + ti.label);
					//System.out.println(ti.instruction + " " + ti.label);

				}
				else if (ti.instruction != null && ti.operand1 != null && ti.operand2 != null)
				{
					pw.println(ti.instruction + " " + ti.operand1 + ", " + ti.operand2);
					//System.out.println(ti.instruction + " " + ti.operand1 + ", " + ti.operand2);
				}
				else if (ti.label != null)
				{
					pw.println(ti.label);
					//System.out.println(ti.label);
				}
				else 
				{
					System.out.println("ERROR");
				}

			}
		}
		int numOfVars = cdx.vars.size();
		int sizeToAlloc = numOfVars * 4;
		sizeToAlloc += 8;
		pw.println("main:");
		
		pw.println("pushl $" + sizeToAlloc);
		pw.println("pushl $1");
		pw.println("call calloc");
		pw.println("addl $8, %esp");
		pw.println("pushl %eax");
		pw.println("call _class_" + lastClass + "_method_start");
		pw.println("popl %eax");
		pw.println("ret");
		pw.close();
		//System.out.println("ret");
	}
	
	
	public boolean checkForGap()
	{
		Integer lastDataFound = 0;
		Integer newFoundData = 0;
		for (int i = 0; i < instructions.size(); i++)
		{
			String directive;
			if(instructions.get(i) != null && instructions.get(i).directive != null)
			{
				directive = instructions.get(i).directive;
				if(directive.equals(".data"))
				{
					newFoundData = i;
					if(newFoundData != lastDataFound || newFoundData != lastDataFound + 1)
					{
						return true;
					}
					lastDataFound = i;
				}
			}
			
		}
		return false;
	}
	
	
	public void sortInstructions()
	{
		ArrayList<TargetInstruction> varDecls = new ArrayList<>();
		for(TargetInstruction ti : instructions)
		{
			if(ti.directive != null && ti.directive.equals(".data"))
			{
				varDecls.add(ti);
			}
		}
		ArrayList<TargetInstruction> updatedInstructions = new ArrayList<>();
		for(TargetInstruction vd : varDecls)
		{
			if(vd.directive.equals(".data"))
			{
				updatedInstructions.add(vd);
			}
		}
		for(TargetInstruction ti : instructions)
		{
			if(ti.directive == null || !ti.directive.equals(".data"))
			{
				updatedInstructions.add(ti);
			}
		}
		instructions = new ArrayList<>();
		instructions = updatedInstructions;
	}
	
	
	public void emit(String instruction, String label)
	{
		TargetInstruction targetInstruction = new TargetInstruction();
		targetInstruction.instruction = instruction;
		targetInstruction.label = label;
		String comStr = ".comm";
		String dataDirectiveStr = ".data";
		String textDirectiveStr = ".text";
		// variable declaration (only works for int and boolean, next phase will deal with string)
		if (instruction.equals(comStr))
		{
			targetInstruction.directive = dataDirectiveStr;
			targetInstruction.label = "_" + label + ", 4, 4";
		}
		else
		{
			targetInstruction.directive = textDirectiveStr;
		}
		instructions.add(targetInstruction);
		
	}
	public void emitComment(String comment)
	{
		TargetInstruction targetInstruction = new TargetInstruction();
		targetInstruction.comment = comment;
		instructions.add(targetInstruction);
	}
	
	public void emitLabel(String label)
	{
		TargetInstruction targetInstruction = new TargetInstruction();
		targetInstruction.directive = ".text";
		targetInstruction.label = label;
		instructions.add(targetInstruction);
	}
	
	public void emit(String instruction, String operand1, String operand2)
	{
		TargetInstruction targetInstruction = new TargetInstruction();
		targetInstruction.directive = ".text";
		targetInstruction.instruction = instruction;
		targetInstruction.operand1 = operand1;
		targetInstruction.operand2 = operand2;
		instructions.add(targetInstruction);
	}
	
	public String generateComment(ParserRuleContext ctx)
	{
		String comment = "";
		String lineNum =  String.valueOf(ctx.getStart().getLine());
		String lineText = ctx.getText();
		String hashtags = 	"############################\n";
		comment = 	"# Line " + lineNum + ": ";
		String[] lines = lineText.split("\n");
		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i];
			if (i != 0)
			{
				comment = comment + "# " + line + "\n";
			}
			else
			{
				comment = comment + line + "\n";
			}
		}
		
		
		String fullComment = hashtags + comment + hashtags;
		return fullComment;
	}


	@Override
	public Double visitVar_decl(Var_declContext ctx) {
		String fullComment = generateComment(ctx);
		Symbol s = ctx.sym;
		if (s != null && s.scopeNum == LOCAL)
		{
			Integer offset = s.varDecl.offSet;
			emitComment(fullComment);
			emit("movl", "$0", offset + "(%ebp)" );

		}
		else if (s.scopeNum == CLASS)
		{
			Integer offset = s.varDecl.offSet;
			emitComment(fullComment);
			
		}
		else
		{
			String label = ctx.IDENTIFIER().getText();
			String instruction = ".comm";
			emitComment(fullComment);
			emit(instruction, label);
		}
		
		return null;
	}
	
	
	


	@Override
	public Double visitClass_decl(Class_declContext ctx) {
		lastClass = ctx.id1.getText();
		cdx = ctx;
		
		return super.visitClass_decl(ctx);
	}


	@Override
	public Double visitMethod_decl(Method_declContext ctx) {
		String fullComment = generateComment(ctx);
		String label = ctx.id1.getText();
		String directive = ".global";
		String type = ".type";
		Class_declContext cdx = (Class_declContext) ctx.getParent();
		String className = cdx.id1.getText();
		label = "_class_" + className + "_method_" + label;
		String op1 = label;
		String op2 = "@function";
		
		emitComment(fullComment);
		emit(directive, label);
		emit(type, op1, op2);
		emitLabel(label + ":");
		emit("pushl", "%ebp");
		emit("movl", "%esp", "%ebp");
		Integer numLocals = 0;
		//visit(ctx.argument_decl_list());
		// probably visit(arg_decl_list)
		for (Var_declContext vdc : ctx.vars)
		{
			numLocals += 1;
		}
		Integer reserveSize = 4;
		reserveSize += 4 * numLocals;
		emit("subl", "$" + reserveSize, "%esp");
		for (Var_declContext vdc : ctx.vars)
		{
			visit(vdc);
		}
		for (StatementContext stmtc : ctx.statement_list().stmts)
		{
			
			visit(stmtc);
		}
		emit("movl", "%ebp", "%esp");
		emit("movl", "-4(%ebp)", "%eax");
		emit("popl", "%ebp");
		emit("ret", "");
		return null;
	}
	
	


	


	


	@Override
	public Double visitAssignment_stmt(Assignment_stmtContext ctx) {
		String fullComment = generateComment(ctx);
		emitComment(fullComment);
		visit(ctx.e2);
		// check if right side is a method call
		boolean rightSideIsMethod = false;
		Symbol s = ctx.sym;

		Symbol sym = ctx.e2.sym;
		if (sym != null && sym.methodDecl != null && sym.methodDecl.methodDecl)
		{
			//s = ctx.sym;
			
			rightSideIsMethod = true;
			/*
			Integer offset;
			if (s.varDecl != null )
			{
				offset = s.varDecl.offSet;

			}
			else
				
			{
				offset = -4;
			}
			emit("movl", "%eax",  offset + "(%ebp)" );
			*/
		}
		if (s != null && s.scopeNum == LOCAL)
		{
			Integer offset = s.varDecl.offSet;
			if (!rightSideIsMethod)
			{
				emit("popl", "%eax");

			}
			emit("movl", "%eax", offset + "(%ebp)" );
		}
		
		else if (s.methodDecl != null)
		{
			Integer offset = -4;
			if (!rightSideIsMethod)
			{
				emit("popl", "%eax");

			}			
			emit("movl", "%eax", offset + "(%ebp)");
			
		}
		else if (s != null && s.scopeNum == CLASS)
		{
			if (!rightSideIsMethod)
			{
				emit("popl", "%eax");

			}
			emit("movl", "8(%ebp)", "%ebx");
			emit("movl", "%eax" , s.varDecl.offSet + "(%ebx)");
			//emit("pushl", "%eax");
		}
		else
		{
			String label = "_" + ctx.IDENTIFIER().getText();
			if (!rightSideIsMethod)
			{
				emit("popl", "%eax");

			}			
			emit("movl", "%eax, " + label);
		}
		
		
		
		return null;
	}
	
	


	@Override
	public Double visitIf_stmt(If_stmtContext ctx) {
		String fullComment = generateComment(ctx);
		emitComment(fullComment);
		Integer lineNum = ctx.start.getLine();
		visit(ctx.expression());
		emit("popl", "%eax");
		emit("cmpl", "$0", "%eax");
		String doIfLabel = "_doifLine" + lineNum; 
		String elseLabel = "_elseLine" + lineNum;
		String endIfLabel = "_endIfLine" + lineNum;
		emit("jne", doIfLabel);
		emit("jmp", elseLabel);
		emitLabel(doIfLabel + ":");
		for (int i = 0; i < ctx.sl1.stmts.size(); i++)
		{
			StatementContext sctx = ctx.sl1.stmts.get(i);
			visit(sctx);
		}
		emit("jmp", endIfLabel);
		emitLabel(elseLabel + ":");
		if (ctx.sl2 != null)
		{
			for (int i = 0; i < ctx.sl2.stmts.size(); i++)
			{
				StatementContext sctx = ctx.sl2.stmts.get(i);
				visit(sctx);
			}
		}
		
		emitLabel(endIfLabel + ":");
		
		return null;
	}
	
	


	@Override
	public Double visitLoop_stmt(Loop_stmtContext ctx) {
		String fullComment = generateComment(ctx);
		emitComment(fullComment);
		Integer lineNum = ctx.start.getLine();
		String whileLabel = "_while" + lineNum;
		String startWhileLabel = "_startwhilebody" + lineNum;
		String endWhileLabel = "_endwhile" + lineNum;
		emitLabel(whileLabel + ":");
		visit(ctx.expression());
		emit("popl", "%eax");
		emit("cmpl", "$0", "%eax");
		emit("jne", startWhileLabel);
		emit("jmp", endWhileLabel);
		emitLabel(startWhileLabel + ":");
		for (int i = 0; i < ctx.statement_list().stmts.size(); i++)
		{
			StatementContext sctx = ctx.statement_list().stmts.get(i);
			visit(sctx);
		}
		emit("jmp", whileLabel);
		emitLabel(endWhileLabel + ":");	
		return null;
	}
	
	

	public static Class_declContext getClassFromMethod(ParserRuleContext csx)
	{
		Class_declContext cdx = null;
		ParserRuleContext prc = csx.getParent();
		if (prc instanceof Class_declContext)
		{
			return (Class_declContext) prc;
		}
		else
		{
			cdx = getClassFromMethod(prc);
		}
		return cdx;
	}

	@Override
	public Double visitCall_stmt(Call_stmtContext ctx) {
		String fullComment = generateComment(ctx);
		emitComment(fullComment);
		String methodName = ctx.IDENTIFIER().getText();
		Integer numParams = ctx.expression_list().exprs.size();
		Integer paramsSize = numParams * 4;
		switch (methodName)
		{
		case "readint":
			emit("call", "readint");
			break;
		case "writeint": 
			//visit(ctx.expression_list().expression(0));
			for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
			{
				visit(ctx.expression_list().expression(i));
			}
			// ask schaub if this is correct
			// code to push me goes here
			emit("call", methodName);
			
			emit("addl","$" + paramsSize, "%esp" );
			
			break;
		default:
			
			Class_declContext cdx = getClassFromMethod(ctx);
			String className = cdx.id1.getText();
			
			methodName = "_class_" + className + "_method_" + methodName; 
			// call that looks like boo()
			if (ctx.expression() == null || (ctx.expression() != null && ctx.expression().getText().equals("")))
			{
				// if there are params
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// ask schaub if this is correct
					// code to push me goes here
					emit("pushl", "8(%ebp)");

					emit("call", methodName);
					paramsSize += 4;
					emit("addl","$" + paramsSize, "%esp" );
				}
				else // no params
				{
					emit("pushl", "8(%ebp)");

					emit("call", methodName);
					paramsSize += 4;
					emit("addl","$" + paramsSize, "%esp" );					
				}
			}
			// call that looks like foo.boo()
			else
			{
				Symbol sym = ctx.sym;
				className = "";
				
				className = sym.varDecl.type.name;
				methodName = ctx.IDENTIFIER().getText();
				methodName = "_class_" + className + "_method_" + methodName; 
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// code to push foo part of call
					// ask schaub if this is correct
					

					
					if (sym.scopeNum == LOCAL)
					{
						Integer offset;
						if (sym.varDecl != null )
						{
							offset = sym.varDecl.offSet;

						}
						else
							
						{
							offset = -4;
						}
						emit("pushl", offset + "(%ebp)");
					}
					else if (sym.scopeNum == CLASS)
					{
						emit("movl", "8(%ebp)", "%ebx");

						emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
						emit("pushl", "%eax");
					}
					else
					{
						String label = "";
						label = ctx.IDENTIFIER().toString();
						emit("pushl", "_" + label);
					}
					emit("call", methodName);
					emit("addl","$" + paramsSize, "%esp" );

				}
				else // no params
				{
					
					
					
					if (sym.scopeNum == LOCAL)
					{
						Integer offset;
						if (sym.varDecl != null )
						{
							offset = sym.varDecl.offSet;

						}
						else
							
						{
							offset = -4;
						}
						emit("pushl", offset + "(%ebp)");
					}
					else if (sym.scopeNum == CLASS)
					{
						emit("movl", "8(%ebp)", "%ebx");

						emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
						emit("pushl", "%eax");
					}
					else
					{
						String label = "";
						label = ctx.IDENTIFIER().toString();
						emit("pushl", "_" + label);
					}
					emit("call", methodName);
					emit("addl","$4", "%esp" );

				}
			}
			break;
				
		}
		return null;
	}
	
	@Override
	public Double visitParanNanoterm(ParanNanotermContext ctx) {
		String exprtailStr = ctx.exprtail().getText();
		visit(ctx.expression());

		if (!exprtailStr.equals(""))
		{
			emit("popl", "%eax");
			visit(ctx.exprtail());
		}
		return null;
	}
	
	


	@Override
	public Double visitExprTailExpression(ExprTailExpressionContext ctx) {
		visit(ctx.exprtail());
		return null;
	}
	


	@Override
	public Double visitRegularExprTail(RegularExprTailContext ctx) {
		String methodName = ctx.IDENTIFIER().getText();
		switch (methodName)
		{
		case "readint":
			emit("call", "readint");
			// added this line here and not in call statement because here the value is necessary
			emit("pushl", "%eax");
			break;
		case "writeint": 
			visit(ctx.expression_list().expression(0));
			emit("call", "writeint");
			emit("popl", "%ecx");
			break;
		default:
			Class_declContext cdx = getClassFromMethod(ctx);
			String className = cdx.id1.getText();
			methodName = "_class_" + className + "_method_" + methodName; 
			Integer numParams = ctx.expression_list().exprs.size();
			Integer paramsSize = numParams * 4;
			// call that looks like boo()
			// if there are params
			String parentString = ctx.getParent().getText();
			Symbol s = ctx.sym;
			if (s.classDecl == null && s.methodDecl != null)
			{
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// ask schaub if this is correct
					// code to push me goes here
					emit("pushl", "8(%ebp)");
					paramsSize += 4;
					emit("call", methodName);
					
					emit("addl","$" + paramsSize, "%esp" );
					
				}
				// if there are not params
				else 
				{
					emit("pushl", "8(%ebp)");

					emit("call", methodName);
					paramsSize += 4;
					emit("addl","$" + paramsSize, "%esp" );			
				}
			}
			else
			{
				Symbol sym = ctx.sym;
				className = "";
				className = sym.classDecl.className;
				methodName = ctx.IDENTIFIER().getText();
				methodName = "_class_" + className + "_method_" + methodName; 
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// code to push foo part of call
					// ask schaub if this is correct
					

					paramsSize = paramsSize + 4;
					if (sym.scopeNum == LOCAL)
					{
						Integer offset;
						if (sym.varDecl != null )
						{
							offset = sym.varDecl.offSet;

						}
						else
							
						{
							offset = -4;
						}
						emit("pushl", offset + "(%ebp)");
					}
					else if (sym.scopeNum == CLASS)
					{
						emit("movl", "8(%ebp)", "%ebx");

						emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
						emit("pushl", "%eax");
					}
					else if (sym.classDecl != null)
					{
						emit("pushl", "%eax");
					}
					else
					{
						String label = "";
						label = ctx.IDENTIFIER().toString();
						emit("pushl", "_" + label);
					}
					emit("call", methodName);
					emit("addl","$" + paramsSize, "%esp" );

				}
				else // no params
				{
					
								
					if (sym.scopeNum == LOCAL)
					{
						Integer offset;
						if (sym.varDecl != null )
						{
							offset = sym.varDecl.offSet;

						}
						else
							
						{
							offset = -4;
						}
						emit("pushl", offset + "(%ebp)");
					}
					else if (sym.scopeNum == CLASS)
					{
						emit("movl", "8(%ebp)", "%ebx");

						emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
						emit("pushl", "%eax");
					}
					else
					{
						String label = "";
						label = ctx.IDENTIFIER().toString();
						emit("pushl", "_" + label);
					}
					emit("call", methodName);
					emit("addl","$4", "%esp" );

				}
			}
			
			
			
				
			

			
			
			// call that looks like foo.boo
			
			
			
			
			
			
			/*
			NormalExpressionContext ec = (NormalExpressionContext) ctx.getParent();
			String classReferenced = "";
			if (ec.IDENTIFIER() != null)
			{
				classReferenced = ec.IDENTIFIER().getText();
			}
			
			// if (ctx.parent.getChild(0))
			if (classReferenced.equals(""))
			{
				// if there are params
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// ask schaub if this is correct
					// code to push me goes here
					emit("call", methodName);
					
					emit("addl","$" + paramsSize, "%esp" );
				}
				// if there are not params
				else 
				{
					emit("call", methodName);
				}
			}
			// call that looks like foo.boo()
			else
			{
				// if there are params
				if (!ctx.expression_list().getText().equals(""))
				{
					for (int i = ctx.expression_list().exprs.size() - 1; i > -1; i--)
					{
						visit(ctx.expression_list().expression(i));
					}
					// code to push foo part of call
					// ask schaub if this is correct
					emit("call", methodName);
					emit("addl","$" + paramsSize, "%esp" );

				}
				else
				{
					emit("call", methodName);
				}
			}*/
			if (ctx.sym.methodDecl == null && ctx.sym.classDecl != null)
			{
				Symbol sym = ctx.sym;
				ClassDecl cd = sym.classDecl;
				MethodDecl md = null;
				methodName = "";
				methodName = ctx.IDENTIFIER().getText();
				for (int i = 0; i < cd.methods.size(); i++)
				{
					MethodDecl mdl = cd.methods.get(i);
					if (mdl.methodName.equals(methodName))
					{
						md = mdl;
						break;
					}
				}
				if (md != null && md.returnType != Type.VOID)
				{
					emit("pushl", "%eax");

				}
				
			}
			
			else if (ctx.sym.methodDecl != null && ctx.sym.methodDecl.returnType != Type.VOID)
			{
				emit("pushl", "%eax");

			}
			break;
		}
		return null;
	}


	@Override
	public Double visitNormalExpression(NormalExpressionContext ctx) {
		//String fullComment = generateComment(ctx);
		//emit(fullComment);
		String exprTailStr = ctx.exprtail().getText();
		if (exprTailStr.equals(""))
		{
			String label;
			if (ctx.IDENTIFIER() != null)
			{
				Symbol sym = ctx.sym;
				if (sym.scopeNum == LOCAL)
				{
					Integer offset;
					if (sym.varDecl != null )
					{
						offset = sym.varDecl.offSet;

					}
					else
						
					{
						offset = -4;
					}
					emit("pushl", offset + "(%ebp)");
				}
				else if (sym.scopeNum == CLASS)
				{
					emit("movl", "8(%ebp)", "%ebx");

					emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
					emit("pushl", "%eax");
				}
				else
				{
					label = ctx.IDENTIFIER().toString();
					emit("pushl", "_" + label);
				}
				
			}
			else if (ctx.STRINGLITERAL() != null)
			{
				// implemented in phase 5
			}
			else if (ctx.INTEGERLITERAL() != null)
			{
				emit("pushl", "$" + ctx.INTEGERLITERAL().getText());
			}
			else if (ctx.TRUE() != null)
			{
				// 0 for false, and 1 for true
				emit("pushl", "$1");
			}
			else if (ctx.FALSE() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.NULL() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.ME() != null)
			{
				emit("pushl", "8(%ebp)");
			}
		}
		else 
		{
			visit(ctx.exprtail());
		}
		
		return null;
	}


	@Override
	public Double visitNewTypeExpression(NewTypeExpressionContext ctx) {
		ClassDecl cd = ctx.sym.classDecl;
		
		
		int numOfVars = cd.variables.size();
		int sizeToAlloc = numOfVars * 4;
		sizeToAlloc += 8;
		emit("pushl", "$" + sizeToAlloc);
		emit("pushl", "$1");
		emit("call", "calloc");
		emit("addl", "$8", "%esp");
		emit("pushl", "%eax");
		/*
		pw.println("main:");
		
		pw.println("pushl $" + sizeToAlloc);
		pw.println("pushl $1");
		pw.println("call calloc");
		pw.println("addl $8, %esp");
		pw.println("pushl %eax");
		pw.println("call _class_" + lastClass + "_method_start");
		pw.println("popl %eax");
		pw.println("ret");
		pw.close();
		*/
		return null;
		//return super.visitNewTypeExpression(ctx);
	}


	@Override
	public Double visitOrExpression(OrExpressionContext ctx) {
		//String fullMessage = generateComment(ctx);
		//emit(fullMessage);
		visit(ctx.expression());
		visit(ctx.factor());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		emit("orl", "%eax", "%ebx");
		emit("pushl", "%eax");
		return null;
		
	}


	@Override
	public Double visitFactorExpression(FactorExpressionContext ctx) {
		visit(ctx.factor());
		// may not be correct; bug
		// emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitParenthesizedExpression(ParenthesizedExpressionContext ctx) {
		String exprtailStr = ctx.exprtail().getText();
		visit(ctx.expression());

		if (!exprtailStr.equals(""))
		{
			emit("popl", "%eax");
			visit(ctx.exprtail());
		}
		
		return null;
	}


	@Override
	public Double visitAndFactoidFactor(AndFactoidFactorContext ctx) {
		visit(ctx.factor());
		visit(ctx.factoid());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		emit("andl", "%eax", "%ebx");
		emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitAndExpressionFactor(AndExpressionFactorContext ctx) {
		visit(ctx.factor());
		visit(ctx.expression());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		emit("andl", "%eax", "%ebx");
		emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitFactoidFactor(FactoidFactorContext ctx) {
		visit(ctx.factoid());
		return null;
	}


	@Override
	public Double visitNanotermFactoid(NanotermFactoidContext ctx) {
		visit(ctx.n1);
		visit(ctx.n2);
		emit("popl", "%ebx");
		emit("popl", "%eax");
		String operator = ctx.relational_op().getText();
		String lineNum = String.valueOf(ctx.start.getLine());
		emit("cmpl", "%ebx", "%eax");
		switch (operator)
		{
		case ">":
			String greaterLabel = "_greaterLine" + lineNum; 
			String notGreaterLabel = "_notGreaterLine" + lineNum;
			String endGreaterLabel = "_endGreaterLine" + lineNum;
			emit("jg", greaterLabel);
			emit("jmp", notGreaterLabel);
			emitLabel(greaterLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endGreaterLabel);
			emitLabel(notGreaterLabel + ":");
			emit("pushl", "$0");
			emitLabel(endGreaterLabel + ":");
			break;
		case ">=":
			String greaterOrEqLabel = "_greaterOrEqLine" + lineNum; 
			String notGreaterOrEqLabel = "_notGreaterOrEqLine" + lineNum;
			String endGreaterOrEqLabel = "_endGreaterOrEqLine" + lineNum;
			emit("jge", greaterOrEqLabel);
			emit("jmp", notGreaterOrEqLabel);
			emitLabel(greaterOrEqLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endGreaterOrEqLabel);
			emitLabel(notGreaterOrEqLabel + ":");
			emit("pushl", "$0");
			emitLabel(endGreaterOrEqLabel + ":");
			break;
		case "=":
			String eqLabel = "_eqLine" + lineNum; 
			String notEqLabel = "_notEqLine" + lineNum;
			String endEqLabel = "_endEqLine" + lineNum;
			emit("je", eqLabel);
			emit("jmp", notEqLabel);
			emitLabel(eqLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endEqLabel);
			emitLabel(notEqLabel + ":");
			emit("pushl", "$0");
			emitLabel(endEqLabel + ":");
			break;
		}
		
		return null;
	}


	@Override
	public Double visitExpressionFactoid(ExpressionFactoidContext ctx) {
		visit(ctx.nanoterm());
		visit(ctx.expression());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		String operator = ctx.relational_op().getText();
		String lineNum = String.valueOf(ctx.start.getLine());
		emit("cmpl", "%ebx", "%eax");
		switch (operator)
		{
		case ">":
			String greaterLabel = "_greaterLine" + lineNum; 
			String notGreaterLabel = "_notGreaterLine" + lineNum;
			String endGreaterLabel = "_endGreaterLine" + lineNum;
			emit("jg", greaterLabel);
			emit("jmp", notGreaterLabel);
			emitLabel(greaterLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endGreaterLabel);
			emitLabel(notGreaterLabel + ":");
			emit("pushl", "$0");
			emitLabel(endGreaterLabel + ":");
			break;
		case ">=":
			String greaterOrEqLabel = "_greaterOrEqLine" + lineNum; 
			String notGreaterOrEqLabel = "_notGreaterOrEqLine" + lineNum;
			String endGreaterOrEqLabel = "_endGreaterOrEqLine" + lineNum;
			emit("jge", greaterOrEqLabel);
			emit("jmp", notGreaterOrEqLabel);
			emitLabel(greaterOrEqLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endGreaterOrEqLabel);
			emitLabel(notGreaterOrEqLabel + ":");
			emit("pushl", "$0");
			emitLabel(endGreaterOrEqLabel + ":");
			break;
		case "=":
			String eqLabel = "_eqLine" + lineNum; 
			String notEqLabel = "_notEqLine" + lineNum;
			String endEqLabel = "_endEqLine" + lineNum;
			emit("je", eqLabel);
			emit("jmp", notEqLabel);
			emitLabel(eqLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endEqLabel);
			emitLabel(notEqLabel + ":");
			emit("pushl", "$0");
			emitLabel(endEqLabel + ":");
			break;
		}
		
		return null;
	}


	@Override
	public Double visitBbyteFactoid(BbyteFactoidContext ctx) {
		visit(ctx.bbyte());
		return null;
	}


	@Override
	public Double visitConcatNybbleBbyte(ConcatNybbleBbyteContext ctx) {
		// phase 5
		return super.visitConcatNybbleBbyte(ctx);
	}


	@Override
	public Double visitNybbleBbyte(NybbleBbyteContext ctx) {
		visit(ctx.nybble());
		return null;
	}


	@Override
	public Double visitAdOpTermNybble(AdOpTermNybbleContext ctx) {
		visit(ctx.nybble());
		visit(ctx.term());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		String operator = ctx.add_op().getText();
		switch (operator)
		{
		case "+":
			emit("addl", "%ebx", "%eax");
			break;
		case "-":
			emit("subl", "%ebx", "%eax");
			break;
		}
		emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitAdopExpressionNybble(AdopExpressionNybbleContext ctx) {
		visit(ctx.nybble());
		visit(ctx.expression());
		emit("popl", "%ebx");
		emit("popl", "%eax");
		String operator = ctx.add_op().getText();
		switch (operator)
		{
		case "+":
			emit("addl", "%ebx", "%eax");
			break;
		case "-":
			emit("subl", "%ebx", "%eax");
			break;
		}
		emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitMulOpMicrotermTerm(MulOpMicrotermTermContext ctx) {
		// visit in inverse order since i am using 'call'
		visit(ctx.microterm());
		visit(ctx.term());
		String operator = ctx.mul_op().getText();
		switch (operator)
		{
		case "*":
			emit("call", "multiply");
			emit("popl", "%ecx");
			emit("popl", "%ecx");
			break;
		case "/":
			emit("call", "divide");
			emit("popl", "%ecx");
			emit("popl", "%ecx");
			break;
		}
		emit("pushl", "%eax");
		return null;
	}


	@Override
	public Double visitMulOPExpressionTerm(MulOPExpressionTermContext ctx) {
		// visit in inverse order since i am using 'call'
				visit(ctx.expression());
				visit(ctx.term());
				String operator = ctx.mul_op().getText();
				switch (operator)
				{
				case "*":
					emit("call", "multiply");
					emit("popl", "%ecx");
					emit("popl", "%ecx");
					break;
				case "/":
					emit("call", "divide");
					emit("popl", "%ecx");
					emit("popl", "%ecx");
					break;
				}
				emit("pushl", "%eax");
				return null;
	}


	@Override
	public Double visitMicrotermTerm(MicrotermTermContext ctx) {
		visit(ctx.microterm());
		return null;
	}


	@Override
	public Double visitUnaryOpRegularMicroterm(UnaryOpRegularMicrotermContext ctx) {
		String label;
		String lineNum = String.valueOf(ctx.getStart().getLine());
		String op = ctx.unary_op().getText();
		if (ctx.exprtail() != null && !ctx.exprtail().getText().equals(""))
		{
			visit(ctx.exprtail());
		}
		else
		{
			if (ctx.IDENTIFIER() != null)
			{
				Symbol sym = ctx.sym;
				if (sym.scopeNum == LOCAL)
				{
					Integer offset;
					if (sym.varDecl != null )
					{
						offset = sym.varDecl.offSet;

					}
					else
						
					{
						offset = -4;
					}					emit("pushl", offset + "(%ebp)");
				}
				else if (sym.scopeNum == CLASS)
				{
					emit("movl", "8(%ebp)", "%ebx");

					emit("movl", "%eax" , sym.varDecl.offSet + "(%ebx)");
					emit("pushl", "%eax");
				}
				else
				{
					label = ctx.IDENTIFIER().toString();
					emit("pushl", "_" + label);
				}
				emit("popl", "%eax");
				switch (op)
				{
				case "+":
					emit("pushl", "%eax");
					break;
				case "-":
					emit("negl", "%eax");
					emit("pushl", "%eax");
					break;
				case "not":
					emit("cmpl", "$0", "%eax");
					String eqLabel = "_eqLine" + lineNum + "." + notNum; 
					String notEqLabel = "_notEqLine" + lineNum+ "." + notNum;
					String endEqLabel = "_endEqLine" + lineNum+ "." + notNum;
					emit("je", eqLabel);
					emit("jmp", notEqLabel);
					emitLabel(eqLabel + ":");
					emit("pushl", "$1");
					emit("jmp", endEqLabel);
					emitLabel(notEqLabel + ":");
					emit("pushl", "$0");
					emitLabel(endEqLabel + ":");
					notNum++;
					break;
				}
			}
			else if (ctx.STRINGLITERAL() != null)
			{
				// implemented in phase 5
			}
			else if (ctx.INTEGERLITERAL() != null)
			{
				String integer = ctx.INTEGERLITERAL().getText();
				switch (op)
				{
				case "+":
					emit("pushl", "$" + integer);
					break;
				case "-":
					emit("pushl", "$" + integer);
					emit("popl", "%eax");
					emit("negl", "%eax");
					emit("pushl", "%eax");
					break;
				
				}
			}
			else if (ctx.TRUE() != null)
			{
				// 0 for false, and 1 for true put it backwards here because the only op
				// possible here would be 'not'
				emit("pushl", "$0");
			}
			else if (ctx.FALSE() != null)
			{
				emit("pushl", "$1");
			}
			else if (ctx.NULL() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.ME() != null)
			{
				// - me is not permitted
			}
		}
		
		return null;
	}


	@Override
	public Double visitUnaryOpExpressionMicroterm(UnaryOpExpressionMicrotermContext ctx) {
		visit(ctx.expression());
		String op = ctx.unary_op().getText();
		String lineNum = String.valueOf(ctx.getStart().getLine());
		emit("popl", "%eax");
		switch (op)
		{
		case "+":
			emit("pushl", "%eax");
			break;
		case "-":
			emit("negl", "%eax");
			emit("pushl", "%eax");
			break;
		case "not":
			emit("cmpl", "$0", "%eax");
			String eqLabel = "_eqLine" + lineNum + "." + notNum; 
			String notEqLabel = "_notEqLine" + lineNum + "." + notNum;
			String endEqLabel = "_endEqLine" + lineNum + "." + notNum;
			emit("je", eqLabel);
			emit("jmp", notEqLabel);
			emitLabel(eqLabel + ":");
			emit("pushl", "$1");
			emit("jmp", endEqLabel);
			emitLabel(notEqLabel + ":");
			emit("pushl", "$0");
			emitLabel(endEqLabel + ":");
			notNum++;
		}
		return null;
	}


	@Override
	public Double visitRegularMicroterm(RegularMicrotermContext ctx) {
		String label;
		if (ctx.exprtail() != null && !ctx.exprtail().getText().equals(""))
		{
			visit(ctx.exprtail());
		}
		else
		{
			if (ctx.IDENTIFIER() != null)
			{
				Symbol sym = ctx.sym;
				if (sym.scopeNum == LOCAL)
				{
					Integer offset;
					if (sym.varDecl != null )
					{
						offset = sym.varDecl.offSet;

					}
					else
						
					{
						offset = -4;
					}					
					emit("pushl", offset + "(%ebp)");
				}
				else if (sym.scopeNum == CLASS)
				{
					emit("movl", "8(%ebp)", "%ebx");

					emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
					emit("pushl", "%eax");
				}
				else
				{
					label = ctx.IDENTIFIER().toString();
					emit("pushl", "_" + label);
				}
			}
			else if (ctx.STRINGLITERAL() != null)
			{
				// implemented in phase 5
			}
			else if (ctx.INTEGERLITERAL() != null)
			{
				emit("pushl", "$" + ctx.INTEGERLITERAL().getText());
			}
			else if (ctx.TRUE() != null)
			{
				// 0 for false, and 1 for true
				emit("pushl", "$1");
			}
			else if (ctx.FALSE() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.NULL() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.ME() != null)
			{
				emit("pushl", "8(%ebp)");
			}
		}
		
		return null;
	}


	@Override
	public Double visitNanotermMicroterm(NanotermMicrotermContext ctx) {
		visit(ctx.nanoterm());
		return null;
	}


	@Override
	public Double visitRegularNanoterm(RegularNanotermContext ctx) {
		String label;
		if (ctx.exprtail() != null && !ctx.exprtail().getText().equals(""))
		{
			visit(ctx.exprtail());
		}
		else 
		{
			if (ctx.IDENTIFIER() != null)
			{
				Symbol sym = ctx.sym;
				if (sym.scopeNum == LOCAL)
				{
					Integer offset;
					if (sym.varDecl != null )
					{
						offset = sym.varDecl.offSet;

					}
					else
						
					{
						offset = -4;
					}
					emit("pushl", offset + "(%ebp)");
				}
				else if (sym.scopeNum == CLASS)
				{
					emit("movl", "8(%ebp)", "%ebx");

					emit("movl", sym.varDecl.offSet + "(%ebx)", "%eax" );
					emit("pushl", "%eax");
				}
				else
				{
					label = ctx.IDENTIFIER().toString();
					emit("pushl", "_" + label);
				}
			}
			else if (ctx.STRINGLITERAL() != null)
			{
				// implemented in phase 5
			}
			else if (ctx.INTEGERLITERAL() != null)
			{
				emit("pushl", "$" + ctx.INTEGERLITERAL().getText());
			}
			else if (ctx.TRUE() != null)
			{
				// 0 for false, and 1 for true
				emit("pushl", "$1");
			}
			else if (ctx.FALSE() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.NULL() != null)
			{
				emit("pushl", "$0");
			}
			else if (ctx.ME() != null)
			{
				emit("pushl", "8(%ebp)");
			}
		}
		
		return null;
	}


	
	
	
	
	
	
	
	
}
