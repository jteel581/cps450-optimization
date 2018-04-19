// TargetInstruction.java
// This file holds the TargetInstruction class which is used to hold all the information
// for a given instruction, comment, or label in assembly.
package cps450;

public class TargetInstruction {
	String label;
	String instruction;
	String operand1, operand2;
	String comment;
	String directive;
	
	// This is a constructor for the TargetInstruction class
	public TargetInstruction() {
		this.label = null;
		this.instruction = null;
		this.operand1 = null;
		this.operand2 = null;
		this.comment = null;
		this.directive = null;
	}
	
	
	

}
