// SymbolTable.java
// This is the file that holds the 'SymbolTable' class
// used to hold information about the symbols found in
// the parse tree.
package cps450;

import java.util.Stack;

public class SymbolTable {
	private static SymbolTable st;
	private Integer scopeLevel = 0;
	private Stack<Symbol> symbolStack;  
	public Stack<Symbol> getSymbolStack() {
		return symbolStack;
	}
	// This is a private constructor used only by the 
	// 'getInstance' method.
	private SymbolTable()
	{
		symbolStack = new Stack<Symbol>();
	}
	// This is a method used to get the 
	// single instance of this singleton class.
	public static SymbolTable getInstance()
	{
		if(st == null)
		{
			st = new SymbolTable();
		}
		return st;
	}
	// This method is used to push a symbol onto the 
	// SymbolTable stack, called 'symbolStack'. It takes
	// one parameter, 'name', of type String that sets 
	// the name of the symbol to the value of 'name'.
	// Finally, it returns the pushed symbol.
	public Symbol push(String name)
	{
		Symbol s = new Symbol();
		s.setName(name);
		s.setScopeNum(getScope());
		symbolStack.push(s);
		return s;
		
	}
	// This method is used to push a symbol onto the 
	// SymbolTable stack, called 'symbolStack'. It takes
	// two parameters, 'name', of type String, and 'decl',
	// of type Declaration. It sets the name of the symbol 
	// to the value of 'name' and it sets the attributes
	// using the value of 'decl'.
	// Finally, it returns the pushed symbol.
	public Symbol push(String name, Declaration decl)
	{
		//System.out.println("pushing " + name);
		Symbol s = new Symbol();
		s.setName(name);
		s.setScopeNum(getScope());
		s.decl = decl;
		this.getSymbolStack().push(s);
		return s;
		
	}
	public Symbol push(String name, VarDecl decl)
	{
		//System.out.println("pushing " + name);
		Symbol s = new Symbol();
		s.setName(name);
		s.setScopeNum(getScope());
		s.varDecl = decl;
		this.getSymbolStack().push(s);
		return s;
		
	}
	public Symbol push(String name, MethodDecl decl)
	{
		Symbol s = new Symbol();
		s.setName(name);
		s.setScopeNum(getScope());
		s.methodDecl = decl;
		this.getSymbolStack().push(s);
		return s;
		
	}
	public Symbol push(String name, ClassDecl decl)
	{
		Symbol s = new Symbol();
		s.setName(name);
		s.setScopeNum(getScope());
		s.classDecl = decl;
		this.getSymbolStack().push(s);
		return s;
		
	}
	// This method is used to search the SymbolTable
	// for an entry with the name matching the value
	// of the parameter it takes 'name' of type String.
	// If nothing is found, it returns null.
	public Symbol lookup(String name)
	{
		Symbol s;
		for(int i = st.symbolStack.size() - 1; i > -1; i--)
		{
			s = st.symbolStack.get(i);
			if (s.getName().equals(name))
			{
				return s;
			}
		}
		return null;
	}
	// This method is used to begin a new scope.
	// In order to begin a scope, all that must 
	// be done is incrementing the current scope 
	// by one.
	public void beginScope()
	{
		this.setScopeLevel(getScope() + 1);
	}
	// This method is used to end the current scope.
	// In order to end the current scope, this method
	// first pops all the entries from the SymbolTable
	// that have the current scope. Next, it decrements
	// the scope by one. This method throws an exception
	// if the decremented scope ends up lower than zero.
	public void endScope() throws LessThanZeroException 
	{ 
		if (scopeLevel - 1 < 0)
		{
			throw new LessThanZeroException();
		}
		else
		{
			st.setScopeLevel(scopeLevel - 1);
		}
		boolean keepOnAPoppin = true;
		while(keepOnAPoppin)
		{
			Symbol s = symbolStack.pop();
			if (s.scopeNum <= st.getScope())
			{
				symbolStack.push(s);
				keepOnAPoppin = false;
			}
			else if (symbolStack.isEmpty())
			{
				keepOnAPoppin = false;
			}
		}
		
		
	}
	// This method is used to get the current scope level.
	public Integer getScope() {
		return scopeLevel;
	}
	// This method is used to set the scope level to a new 
	// level. It takes one parameter, 'scopeLevel', of type
	// Integer and it sets the variable scopeLevel to the 
	// value of 'scopeLevel'.
	public void setScopeLevel(Integer scopeLevel) {
		this.scopeLevel = scopeLevel;
	}
	@Override
	public String toString() {
		String symbolTableString = "";
		for (Symbol s : symbolStack)
		{
			symbolTableString = symbolTableString.concat("Symbol name: " + s.name + " | Scope: " + s.scopeNum + "|\n"); 
		}
		return symbolTableString;
	}
	
	



}
