package cps450;

public class VarDecl extends Declaration {
	
	String name;
	public VarDecl(Type type, String name) {
		super(type);
		this.type = type;
		this.name = name;
		this.varDecl = true;
	}
	
	

}
