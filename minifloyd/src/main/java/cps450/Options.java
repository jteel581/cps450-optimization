// Options.java
// This file is for an 'Options' class used to collect data 
// from arguments passed at runtime.

package cps450;

import java.util.ArrayList;

public class Options 
{
    private ArrayList<String> filenames = new ArrayList<String>();

    // This is a getter method that gets the ArrayList filenames.
    public ArrayList<String> getFileNames()
    {
        return filenames;
    }
    
    // This method checks the arguments passed at runtime to make sure
    // that they are valid. It takes one parameter: 'argumets'
    // It returns an instance of the Options class.
    // The 'arguments' parameter is an array of Strings that are passed as 
    // arguments to the Main method.
    public Options(String[] arguments)
    {
        ds = false;
        dp = false;
        S = false;
        if (arguments.length < 1)
        {
            System.out.println("usage:");
            System.out.println("  lexer <filename>");
            System.out.println("  lexer -option(s) <filename>");
            System.exit(1);
        }
        for(String arg: arguments)
        {
            if(arg.equals("-ds"))
            {
                ds = true;
            }
            else if (arg.equals("-dp"))
            {
                dp = true;
            }
            else if (arg.equals("-S"))
            {
            	S = true;
            }
            else 
            {
                filenames.add(arg);
            }
        }
    }

    private boolean ds = false;

    // This method returns the value of 'ds' which is a boolean 
    // variable that is set true when the -ds option is added
    // and false when the -ds option is left out when running.
    public boolean getDSstatus()
    {
        return ds;
    }

    // This method allows us to see the value of 'ds' which is 
    // a boolean variable that is set true when the -ds option 
    // is added and false when the -ds option is left out when 
    // running.
    public void setDSstatus(boolean b)
    {
        ds = b;
    }  

    private boolean dp = false;

    // This method returns the value of 'dp' which is a boolean 
    // variable that is set true when the -dp option is added
    // and false when the -dp option is left out when running.    
    public boolean getDPstatus()
    {
        return dp;
    }

    // This method returns the value of 'dp' which is a boolean 
    // variable that is set true when the -dp option is added
    // and false when the -dp option is left out when running.
    public void setDPstatus(boolean b)
    {
        dp = b;
    }    
    
    private boolean S = false;

	public boolean getS() {
		return S;
	}

	public void setS(boolean s) {
		S = s;
	}
    
    
}