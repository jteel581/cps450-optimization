// Type.java
// This file holds the Type class.
package cps450;

import java.util.HashMap;

public class Type {
	public static final Type
			ERROR = new Type("<error>"),
			VOID = new Type("void"),
			INT = new Type("int"),
			BOOLEAN = new Type("boolean"),
			STRING = new Type("string");
	protected String name;
	private static HashMap<String, Type> types;
	ClassDecl classDecl;
	
	public static Type createType(ClassDecl cd)
	{
		Type t = new Type(cd.className);
		t.classDecl = cd;
		types.put(cd.className, t);
		return t;
	}
	
	public static Type getTypeForName(String className)
	{
		Type t = null;
		if(types.get(className) != null)
		{
			return types.get(className);
		}
		else 
		{
			return t;
		}
	}
	protected Type(String name) 
	{
		this.name = name;
		types = new HashMap<>();
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
