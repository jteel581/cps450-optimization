// Type.java
// This file holds the Type class.
package cps450;

public class Type {
	public static final Type
			ERROR = new Type("<error>"),
			VOID = new Type("void"),
			INT = new Type("int"),
			BOOLEAN = new Type("boolean"),
			STRING = new Type("string");
	protected String name;
	protected Type(String name) 
	{
		this.name = name;
	}
	@Override
	public String toString() {
		switch (this.name)
		{
		case "<error>":
			return "<error>";
		case "void":
			return "void";
		case "int":
			return "int";
		case "boolean":
			return "boolean";
		case "string":
			return "string";
			
		default:
			return this.name;
		}
	}

}
