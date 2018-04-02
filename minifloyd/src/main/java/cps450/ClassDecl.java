package cps450;

import java.util.ArrayList;

public class ClassDecl extends Declaration {
	String className;
	String superClassName;
	ArrayList<VarDecl> variables;
	ArrayList<MethodDecl> methods;
	public ClassDecl(String className, String superClassName, ArrayList<VarDecl> variables,
			ArrayList<MethodDecl> methods) {
		super(Type.VOID);
		this.className = className;
		this.superClassName = superClassName;
		this.variables = variables;
		this.methods = methods;
		this.classDecl = true;
	}
	public ClassDecl(String className, ArrayList<VarDecl> variables, ArrayList<MethodDecl> methods) {
		super(Type.VOID);
		this.superClassName = null;
		this.className = className;
		this.variables = variables;
		this.methods = methods;
		this.classDecl = true;
	}
	@Override
	public String toString() {
		String classDeclString = "";
		classDeclString = classDeclString.concat("Class Name: " + className + "\n");
		classDeclString = classDeclString.concat("Super Class Name: " + superClassName);
		classDeclString = classDeclString.concat("Variables and Types are: ");
		for (int i = 0; i < variables.size(); i++)
		{
			VarDecl v = variables.get(i);
			classDeclString = classDeclString.concat(v.name + ":" + v.type.toString());
			if (i != variables.size() - 1)
			{
				classDeclString = classDeclString.concat("; ");
			}
			else 
			{
				classDeclString = classDeclString.concat("\n");
			}
		}
		for (int i = 0; i < methods.size(); i++)
		{
			MethodDecl m = methods.get(i);
			System.out.println("TESTINGMETHOD " + m.toString());
			classDeclString = classDeclString.concat(m.toString());
		}
		
		return classDeclString;
	}
	
	

}
