// Main.java
// This is file that holds the 'Main' class and method.

package cps450;
import java.io.IOException;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import cps450.Options;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class Main
{  
    public static void main(String[] arguments) throws IOException, InterruptedException {
        Options ops = new Options(arguments);

        System.out.println();
        // new phase 5 way
        
        for (String file: ops.getFileNames())
        {
    		boolean lastFile = false;
    		Integer numFiles = ops.getFileNames().size();

        	CharStream input = CharStreams.fromFileName(file);
            MyFloydLexer lexer = new MyFloydLexer(input, ops);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FloydParser parser = new FloydParser(tokens);
            // Suppress default error messages
            parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
            // Register my own error handler
            parser.addErrorListener(new MyFloydErrorListener(ops));
            
            ParseTree tree = parser.start();
            
            if (ops.getDPstatus())
            {
            	// Display graphical tree
                Trees.inspect(tree, parser);
            }         
            Integer totalErrors = 0;
            totalErrors =  lexer.numLexicalErrors + parser.getNumberOfSyntaxErrors();
            //System.out.println(totalErrors + " errors found");
            if (totalErrors == 0)
            {
            	SemanticChecker spider = new SemanticChecker(ops);
            	ParseTreeWalker.DEFAULT.walk(spider, tree);
            	totalErrors = spider.numErrors;
            	if (totalErrors != 0)
            	{
                	System.out.println(totalErrors + " errors found");
                	return;

            	}
            	else
            	{
            		if (file.equals(ops.getFileNames().get(numFiles - 1)))
            		{
            			lastFile = true;
            		}
            		CodeGen generator = new CodeGen(ops, file, lastFile);
            		generator.visit(tree);
            		
            		if (generator.checkForGap())
            		{
            			generator.sortInstructions();
            			
            		}
            		generator.outputAssembly();
            	}
            	
            	if (!ops.getS() && lastFile)
            	{
            		//String fileName = ops.getFileNames().get(0);
            		file = file.replace(".floyd", ".s");
            		String stdLib = "stdlib.c";
            		String exeName = "-o" + file.replace(".s", "");
            		String files = "";
            		for (String f : ops.getFileNames())
            		{
            			files += f.replace("floyd", "s");
            			if (!f.equals(ops.getFileNames().get(numFiles - 1)))
            			{
            				files += ", ";
            			}
            					
            		}
            		ProcessBuilder gccLauncher = new ProcessBuilder("gcc", "-m32", files, stdLib, exeName );
            		Process gcc = gccLauncher.start();
            		int code = gcc.waitFor();
            		if (code != 0)
            		{
            			System.out.println("COULD NOT COMPILE-- BOOM!");
            		}
            	}
            		
            		

            }
            else 
            {
            	System.out.println(totalErrors + " errors found");
            }
                   
            
        }
        
        
        
        // old way
        /*
        for (String file: ops.getFileNames())
        {
            CharStream input = CharStreams.fromFileName(file);
            MyFloydLexer lexer = new MyFloydLexer(input, ops);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FloydParser parser = new FloydParser(tokens);
            // Suppress default error messages
            parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
            // Register my own error handler
            parser.addErrorListener(new MyFloydErrorListener(ops));
            
            ParseTree tree = parser.start();
            
            if (ops.getDPstatus())
            {
            	// Display graphical tree
                Trees.inspect(tree, parser);
            }         
            Integer totalErrors = 0;
            totalErrors =  lexer.numLexicalErrors + parser.getNumberOfSyntaxErrors();
            //System.out.println(totalErrors + " errors found");
            if (totalErrors == 0)
            {
            	SemanticChecker spider = new SemanticChecker(ops);
            	ParseTreeWalker.DEFAULT.walk(spider, tree);
            	totalErrors = spider.numErrors;
            	if (totalErrors != 0)
            	{
                	System.out.println(totalErrors + " errors found");

            	}
            	else
            	{
            		CodeGen generator = new CodeGen(ops);
            		generator.visit(tree);
            		if(ops.getS())
            		{
            			// output to file but don't invoke gcc
            			if (generator.checkForGap())
            			{
            				generator.sortInstructions();
            			}
                		generator.outputAssembly();

            		}
            		else
            		{
            			// output to file AND invoke gcc
            			if (generator.checkForGap())
            			{
            				generator.sortInstructions();
            			}
                		generator.outputAssembly();
                		String fileName = ops.getFileNames().get(0);
                		fileName = fileName.replace(".floyd", ".s");
                		String stdLib = "stdlib.c";
                		String exeName = "-o" + fileName.replace(".s", "");
                		ProcessBuilder gccLauncher = new ProcessBuilder("gcc", "-m32", fileName, stdLib, exeName );
                		Process gcc = gccLauncher.start();
                		int code = gcc.waitFor();
                		if (code != 0)
                		{
                			System.out.println("COULD NOT COMPILE-- BOOM!");
                		}
                		
            			
            		}
            		
            	}

            }
            else 
            {
            	System.out.println(totalErrors + " errors found");
            }
        
        }*/
    }
    
    
}

