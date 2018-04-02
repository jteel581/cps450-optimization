// Symbol.java
// This file holds the Symbol class.
package cps450;

public class Symbol {
	Integer scopeNum = -1;
	String name = "";
	Declaration decl;
	MethodDecl methodDecl;
	VarDecl varDecl;
	ClassDecl classDecl;
	//Type attributes = new Type();
	
	// This is a getter method for the 'scopeNum'
	// variable that every Symbol has. It returns
	// the scopeNum variable of the Symbol instance 
	// it is called on.
	public Integer getScopeNum() {
		return scopeNum;
	}
	// This is a setter method for the 'scopeNum'
	// variable that every Symbol has. It takes
	// one parameter 'scopeNum' of type Integer
	// and sets the value of scopeNum to the value 
	// passed in 'scopeNum'.
	public void setScopeNum(Integer scopeNum) {
		this.scopeNum = scopeNum;
	}
	// This is a getter method for the 'name'
	// variable that every Symbol has. It returns
	// the name variable of the Symbol instance 
	// it is called on.
	public String getName() {
		return name;
	}
	// This is a setter method for the 'name'
	// variable that every Symbol has. It takes
	// one parameter 'name' of type String
	// and sets the value of name to the value 
	// passed in 'name'.
	public void setName(String name) {
		this.name = name;
	}

}
