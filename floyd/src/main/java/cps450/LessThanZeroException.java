// LessThanZeroException.java
// This file is used to hold the cleverly named
// 'LessThanZeroException' class.
package cps450;

public class LessThanZeroException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	// This method is used to print a message when the 
	// LessThanZeroException is thrown.
	public String toString()
	{
		return ("LessThanZeroException occurred: your symbol table scope went below zero!");
	}
	

}
