// Declaration.java
// This is the file that holds the Declaration class.
package cps450;

public class Declaration {
	Type type;
	boolean varDecl = false;
	boolean methodDecl = false;
	boolean classDecl = false;

	public Declaration(Type type) {
		super();
		this.type = type;
	}

	public Declaration() {
		super();
		this.type = null;
	}
	

}
