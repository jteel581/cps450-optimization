// SemanticErrorHandler.java
// This file holds the SemanticErrorHandler class which handles the different semantic errors within
// a given program.s
package cps450;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SemanticErrorHandler {
	SemanticError error;
	Integer numErrors = 0;
	String fileName;
	Integer line;
	Integer column;
	String messageHeader;
	String fullMessage;
	String identifierName;
	String typeOne;
	String typeTwo;
	String undeclaredVarMessage = "Use of undeclared variable ";
	String unsupportedFeatureMessage = "feature unsupported";
	String redeclaredVarMessage = "Redeclaration of variable ";
	String redeclaredMethodMessege = "Redeclaration of method ";
	String typeMismatchMessage = "Type mismatch in assignment statement: expected <t1> on RHS, got <t2>";
	String incorrectOperandTypeMessage = "Incorrect type for <op>: requires <t1>, given <t2>";
	String undeclaredMethodMessage = "Use of undeclared method ";
	String wrongParamNumMessage = "Expected <n1> parameters in call to <m>(); received <n2>";
	String wrongParamTypeMessage = "Expected paramater(s) of type(s) (<t1>) in call to <m>(); received (<t2>)";
	String moreThanOneClassMessage = "Failure to define exatly one class in a source file";
	String notBooleanMessage = "Expressions in if and loop while tests must be boolean";
	String typeNotSpecifiedMessage = "No type specified";
	String noSuchMethodInClassMessage = "No such method for ";
	String noStartMethodMessage = "The last class defined does not contain a start method.";

	// This is a constructor for the SemanticErrorHandler class
	public SemanticErrorHandler() {
		super();
		this.error = null;
		this.fileName = "";
		this.line = 0;
		this.column = 0;
		this.messageHeader = "";
		this.fullMessage = "";
		this.identifierName = "";
		this.typeOne = "";
		this.typeTwo = "";
		
	}
	
	// These methods are used to report the different errors encountered by printing them to standard out.
	public void reportError(String fileName, ParserRuleContext ctx, SemanticError error, String strOne, String strTwo)
	{
		this.error = error;
		this.fileName = fileName;
		line = ctx.start.getLine();
		column = ctx.start.getCharPositionInLine();
		messageHeader = fileName + ":" + line + "," + column + ":";
		
		switch (error)
		{
		case TYPEMISMATCH:
			fullMessage = messageHeader + typeMismatchMessage;
			fullMessage = fullMessage.replace("<t1>", strOne);
			fullMessage = fullMessage.replace("<t2>", strTwo);
			break;
		case INCORRECTTYPE:
			fullMessage = messageHeader + incorrectOperandTypeMessage;
			if (ctx.getStart().getText().equals("-") || ctx.getStart().getText().equals("+") || ctx.getStart().getText().equals("not"))
			{
				fullMessage = fullMessage.replace("<op>", ctx.children.get(0).getText());

			}
			else
			{
				fullMessage = fullMessage.replace("<op>", ctx.children.get(1).getText());

			}
			fullMessage = fullMessage.replace("<t1>", strOne);
			fullMessage = fullMessage.replace("<t2>", strTwo);
			break;
		case WRONGPARAMNUM:
			fullMessage = messageHeader + wrongParamNumMessage;
			if (ctx.children.get(1).getText().equals("("))
			{
				fullMessage = fullMessage.replace("<m>", ctx.children.get(0).getText());
			}
			else
			{
				fullMessage = fullMessage.replace("<m>", ctx.children.get(2).getText());

			}
			fullMessage = fullMessage.replace("<n1>", strOne);
			fullMessage = fullMessage.replace("<n2>", strTwo);
			break;
		case WRONGPARAMTYPE:
			fullMessage = messageHeader + wrongParamTypeMessage;
			if (ctx.children.get(1).getText().equals("("))
			{
				fullMessage = fullMessage.replace("<m>", ctx.children.get(0).getText());
			}
			else
			{
				fullMessage = fullMessage.replace("<m>", ctx.children.get(2).getText());

			}
			fullMessage = fullMessage.replace("<t1>", strOne);
			fullMessage = fullMessage.replace("<t2>", strTwo);
			break;
		default:
			break; 
		}
		
		
		System.out.println(fullMessage);
		numErrors++;
	}
	
	public void reportError(String fileName, ParserRuleContext ctx, SemanticError error)
	{
		this.error = error;
		this.fileName = fileName;
		line = ctx.start.getLine();
		column = ctx.start.getCharPositionInLine();
		messageHeader = fileName + ":" + line + "," + column + ":";
		switch(error)
		{
		case UNDECLAREDVAR:
			identifierName = ctx.getChild(0).getText();
			fullMessage = messageHeader + undeclaredVarMessage + identifierName;
			break;
		case UNDECLAREDMETHOD:
			identifierName = ctx.getChild(0).getText();
			fullMessage = messageHeader + undeclaredMethodMessage + identifierName;
			break;
		case LOOPTESTNOTBOOL:
			fullMessage = messageHeader + notBooleanMessage;
			break;
		case MORETHANONECLASS:
			fullMessage = messageHeader + moreThanOneClassMessage;
			break;
		case REDECLAREDMETHOD:
			identifierName = ctx.getChild(0).getText();
			fullMessage = messageHeader + redeclaredMethodMessege + identifierName;
			break;
		case REDECLAREDVAR:
			identifierName = ctx.getChild(0).getText();
			fullMessage = messageHeader + redeclaredVarMessage + identifierName;
			break;
		case UNSUPPORTED:
			fullMessage = messageHeader + unsupportedFeatureMessage;
			break;
		case TYPENOTSPECIFIED:
			fullMessage = messageHeader + typeNotSpecifiedMessage;
			break;
		case NOSUCHMETHODINCLASS:
			fullMessage = messageHeader + noSuchMethodInClassMessage + ctx.getChild(0).getText();
			break;
		case NOSTARTMETHOD:
			fullMessage = messageHeader + noStartMethodMessage;
		default:
			break;
		}
		System.out.println(fullMessage);
		numErrors++;


	}
	
	public void reportError(String fileName, TerminalNode assignment) {
		this.fileName = fileName;
		line = assignment.getSymbol().getLine();
		column = assignment.getSymbol().getCharPositionInLine();
		messageHeader = fileName + ":" + line + "," + column + ":";
		fullMessage = messageHeader + unsupportedFeatureMessage;
		System.out.println(fullMessage);
		numErrors++;


	}

}
