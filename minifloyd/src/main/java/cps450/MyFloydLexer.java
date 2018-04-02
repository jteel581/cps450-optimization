// MyFloydLexer.java
// This file is for a subclass of the FloydLexer class
// used to create a lexer for the main parser function.

package cps450;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;

public class MyFloydLexer extends FloydLexer {
	
    boolean dumpTokens;
    Options options;
    Integer numLexicalErrors = 0;
    
    String[] keywords = {"and", "boolean", "begin", "class", "else", "end", "false", "from", "if",
    "inherits", "int", "is", "loop", "me", "new", "not", "null", "or", "string",
    "then", "true", "while"} ;

    String[] operators = {"'&'", "'+'", "'-'", "'*'", "'/'", "'>'", "'>='", "'='"};
    
    String[] miscTokens = {"':='", "'('", "')'", "'['", "']'", "','", "';'", "':'", "'.'" };

    String[] lexicalErrors = {"UNRECOGNIZEDCHAR", "UNTERMINATEDSTRINGLITERAL", "ILLEGALSTRINGLITERAL" };
    
    String[] literals = {"INTEGERLITERAL", "STRINGLITERAL"};

  // This is a constructor of the MyFloydLexer class. It takes two parameters:
  // a CharStream called 'input' and an instance of the Options class called 'ops'.
  // 'input' is used to hold the stream from the file that is being parsed, while
  // 'ops' is used to hold the command line argument processing and results.
	public MyFloydLexer(CharStream input, Options ops) {
        super(input);        
        this.dumpTokens = ops.getDSstatus();
        this.options = ops;
    }
    
    // This method searches a string array 'stringArray' for a string 'str' and 
    // returns true if it is found and false if not.
    public static Boolean contains(String[] stringArray, String str)
    {
    for (Integer i = 0; i < stringArray.length; i++)
    {
        if (stringArray[i].equals(str))
        {
        return true;
        }
    }
    return false;
    }
    
    Vocabulary v = super.getVocabulary();

    
  // This method overrides the current nextToken() method and uses it to lexically 
  // analyze the source code, in addition to the functionality of the -ds option
	@Override
	public Token nextToken() {
        Token t = super.nextToken();
        if (t.getType() == super.EOF)
        {
          return t;
        }

        if (v.getSymbolicName(t.getType()).equals("NEWLINE") && this.options.getDSstatus())
        {
          System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," +  (t.getCharPositionInLine() + 1) +
          ":cr");
        }
        else if (contains(keywords, v.getSymbolicName(t.getType()).toLowerCase()) && this.options.getDSstatus())
        {
          System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
          ":keyword:" + v.getSymbolicName(t.getType()).toLowerCase());
        }
        else if (contains(operators, v.getLiteralName(t.getType())) && this.options.getDSstatus())
        {
          System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
          ":operator:" + v.getLiteralName(t.getType()));
        }
        else if (contains(miscTokens, v.getLiteralName(t.getType())) && this.options.getDSstatus())
        {
          System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
          ":" + v.getLiteralName(t.getType()));
        }
        else if (contains(lexicalErrors, v.getSymbolicName(t.getType())))
        {
            if (v.getSymbolicName(t.getType()).equals("UNRECOGNIZEDCHAR"))
            {
              System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
              ":Unrecognized char: " + t.getText());
              numLexicalErrors++;
              return super.nextToken();
            }
            else if (v.getSymbolicName(t.getType()).equals("UNTERMINATEDSTRINGLITERAL"))
            {
              System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
              ":Unterminated string:" + t.getText());
              numLexicalErrors++;
              return super.nextToken();
            }
            else 
            {
              System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
              ":Illegal string:" + t.getText());
              numLexicalErrors++;
              return super.nextToken();
            }

        }
        else if (contains(literals,v.getSymbolicName(t.getType())) && this.options.getDSstatus())
        {
            if (v.getSymbolicName(t.getType()).equals("INTEGERLITERAL"))
            {
              System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
              ":integer lit:" + t.getText());
            }
            else 
            {
              System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
              ":string lit:" + t.getText());
            }
        }
        else if (v.getSymbolicName(t.getType()).equals("CONTINUATION"))
        {
          return super.nextToken();
        }
        else if (this.options.getDSstatus())
        {
          System.out.println(this.options.getFileNames().get(0) + ":" + t.getLine() + "," + (t.getCharPositionInLine() + 1) +
          ":identifier:" + t.getText());
        }
        return t;      
	}

}
