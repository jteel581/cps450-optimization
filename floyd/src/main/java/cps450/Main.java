// Main.java
// This is file that holds the 'Main' class and method.

package cps450;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
	// This method ended up not being used because I didn't impliment the entire B version, but its purpose is to combine the stdlib.floyd file 
	// with the file that the user entered in order to run the compiler on both of them.
	public static boolean combineFiles(Options ops) throws IOException
	{
		String fileName = ops.getFileNames().get(1);
		String stdLib = "stdlib.floyd";
		String stdLibContents = "";
		File stdLibFile = new File(stdLib);
		BufferedReader libReader = null;
		try {
			libReader = new BufferedReader(new FileReader(stdLibFile));
		} catch (FileNotFoundException e) {
			return false;
		}
		String line = "";
		while((line = libReader.readLine()) != null)
		{
			stdLibContents += line;
			stdLibContents += "\n";
		}
		stdLibContents += "\n";
		File tempFile = new File("temporarycombinedfile.floyd");
		if(!tempFile.exists())
		{
			tempFile.createNewFile();

		}
		
		PrintWriter pw = new PrintWriter("temporarycombinedfile.floyd");
		pw.write(stdLibContents);
		pw.close();
		libReader.close();
		return true;
		
	}
	
	// This method does all the work of compiling so that I can have a small tidy Main method.	
	public static void compile(Options ops) throws IOException, InterruptedException
	{
		for (String file: ops.getFileNames())
        {
        	if (!file.equals("stdlib.floyd"))
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
        	}
        	
            
        
        }
	}
	
	
    public static void main(String[] arguments) throws IOException, InterruptedException {
        Options ops = new Options(arguments);

        System.out.println();
        
        compile(ops);
        
        
       
        
        
    }
    
    
}

