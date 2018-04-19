// SemanticError.java
// This file holds the SemanticError class which is an enumerated type that is used 
// to define all the semantic errors.
package cps450;

public enum SemanticError {
	UNDECLAREDVAR, UNDECLAREDMETHOD, REDECLAREDVAR, REDECLAREDMETHOD, WRONGPARAMNUM,
	MORETHANONECLASS, UNSUPPORTED, INCORRECTTYPE, TYPEMISMATCH, 
	LOOPTESTNOTBOOL, WRONGPARAMTYPE, TYPENOTSPECIFIED, NOSUCHMETHODINCLASS, NOSTARTMETHOD

}
