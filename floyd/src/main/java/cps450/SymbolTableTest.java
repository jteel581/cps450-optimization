// SymbolTableTest.java
// This file holds the SymbolTableTest class
// used to test the SymbolTable class.
package cps450;

import static org.junit.Assert.*;

import org.junit.Test;

public class SymbolTableTest {

	// This method tests the symbol table
	@Test
	public void testSymbolTable() throws LessThanZeroException {
		SymbolTable t = SymbolTable.getInstance();
		Symbol s = t.push("jack");
		assertTrue(s.getName().equals("jack"));
		assertTrue(t.getScope().equals(0));
		assertTrue(s.getScopeNum().equals(0));
		t.beginScope();
		assertTrue(t.getScope().equals(1));
		Symbol s2 = t.push("not Jack");
		assertTrue(s2.getScopeNum().equals(1));
		Symbol s3 = t.push("susie");
		assertTrue(s3.getScopeNum().equals(1));
		Symbol jack = t.lookup("jack");
		assertTrue(jack.scopeNum.equals(0));
		Symbol n = t.lookup("thompson");
		assertTrue(n == null);
		t.endScope();
		assertTrue(t.getScope().equals(0));
		Symbol notThere = t.lookup("not jack");
		assertTrue(notThere == null);
		Symbol lastOne = t.lookup("jack");
		assertTrue(lastOne.getName().equals("jack"));
		assertTrue(lastOne.getScopeNum().equals(0));
	}

}
