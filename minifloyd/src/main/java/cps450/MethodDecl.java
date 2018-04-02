package cps450;

import java.util.ArrayList;

public class MethodDecl extends Declaration {

	String methodName;
	Type returnType;
	ArrayList<String> parameters;
	ArrayList<Type> parameterTypes;
	public MethodDecl(Type returnType, ArrayList<String> parameters, ArrayList<Type> parameterTypes) {
		super(returnType);
		this.returnType = returnType;
		this.parameters = parameters;
		this.parameterTypes = parameterTypes;
		this.methodName = "";
		this.methodDecl = true;
		
	}
	@Override
	public String toString() {
		String methodDeclString = "";
		methodDeclString = methodDeclString.concat("Method Name: " + methodName + "\n");
		methodDeclString = methodDeclString.concat("Return Type: " + returnType.toString() + "\n");
		methodDeclString = methodDeclString.concat("Parameters and Types are: ");
		for (int i = 0; i < parameters.size(); i++)
		{
			String param = parameters.get(i);
			String paramType = parameterTypes.get(i).toString();
			methodDeclString = methodDeclString.concat(param + ":" + paramType);
			if (i != parameters.size() - 1)
			{
				methodDeclString = methodDeclString.concat("; ");
			}
			else 
			{
				methodDeclString = methodDeclString.concat("\n");
			}
		}
		return methodDeclString;


	}
	
	
	

}
