// MethodDecl.java
// This file holds the MethodDecl class which inherits from the Declaration class.
package cps450;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MethodDecl extends Declaration {

	String methodName;
	Type returnType;
	ArrayList<String> parameters;
	ArrayList<Type> parameterTypes;
	Map<String, Integer> offsets;
	Integer offSet;
	
	// This is a constructor for the MethodDecl class
	public MethodDecl(Type returnType, ArrayList<String> parameters, ArrayList<Type> parameterTypes) {
		super(returnType);
		this.returnType = returnType;
		this.parameters = parameters;
		this.parameterTypes = parameterTypes;
		this.methodName = "";
		this.methodDecl = true;
		this.offsets = new HashMap<>();
		this.setUpOffsets();
		
	}
	
	// This method sets the offsets for the different parameters of a method
	private void setUpOffsets()
	{
		Integer offSet = 8;
		for (String param : this.parameters)
		{
			this.offsets.put(param, offSet);
			offSet += 4;
		}
	}
	
	
	public Map<String, Integer> getOffsets() {
		return offsets;
	}
	
	// This is a getter method that gets an offset given the name of the parameter
	public Integer getOffSet(String key)
	{
		Integer offSet = 0;
		offSet = this.offsets.get(key);
		return offSet;
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
