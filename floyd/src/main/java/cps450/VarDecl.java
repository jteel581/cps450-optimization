// VarDecl.java
// This file holds the VarDecl class which inherits from the Declaration class.
package cps450;

public class VarDecl extends Declaration {
	
	String name;
	Integer offSet;
	
	// This is a constructor for the VarDecl class
	public VarDecl(Type type, String name) {
		super(type);
		this.type = type;
		this.name = name;
		this.varDecl = true;
		
	}
	
	// This method sets the offset of a variable
	public void setOffSet(Integer newOffSet)
	{
		this.offSet = newOffSet;
	}
	
	// This method gets the offset of a variable
	public Integer getOffSet()
	{
		return this.offSet;
	}

}
