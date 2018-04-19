// Declaration.java
// This is the file that holds the Declaration class.
package cps450;

public class Declaration {
	Type type;
	boolean varDecl = false;
	boolean methodDecl = false;
	boolean classDecl = false;
	
	// These are a couple of constructors for the Declaration class; One takes a type parameter and the other does not.
	public Declaration(Type type) {
		super();
		this.type = type;
	}

	public Declaration() {
		super();
		this.type = null;
	}
	

}
