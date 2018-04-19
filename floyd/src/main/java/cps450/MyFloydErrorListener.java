// MyFloydErrorListener.java
// This is a file that holds a class that sub-classes the COnsoleErrorListener
// class and changes the way errors are handled.

package cps450;

import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class MyFloydErrorListener extends ConsoleErrorListener {
	
	// This is a constructor for the MyFloydErrorListener class
	MyFloydErrorListener(Options ops)
	{
		options = ops;

	}
	Options options;

	// This overrides the current syntaxError method to use our own error
	// reporting standards. It also prints a stack trace if the -dp option
	// is entered.
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer,
							Object offendingSymbol,
							int line,
							int charPositionInLine,
							String msg,
							RecognitionException e)
	{
		System.err.println(options.getFileNames().get(0) + ":" + line + "," + charPositionInLine + ":" + msg);
		if (e != null && options.getDPstatus())
			e.printStackTrace();
	}
	
}
