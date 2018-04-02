// LexerTest.java
// this is used for a unit test to test the lexer
package cps450;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

public class LexerTest {
	FloydLexer lex;

	@Test
	public void testSuccessfulScan() throws IOException {
		CharStream input = CharStreams.fromStream(
				getClass().getResourceAsStream("phase1test.floyd"));
		lex = new FloydLexer(input);

		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.CLASS);
		assertNextToken(FloydLexer.IDENTIFIER, "Main");
		assertNextToken(FloydLexer.IS);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "x");
		assertNextToken(FloydLexer.COLON);
		assertNextToken(FloydLexer.INT);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "name");
		assertNextToken(FloydLexer.COLON);
		assertNextToken(FloydLexer.STRING);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "start");
		assertNextToken(FloydLexer.OPENPAREN);
		assertNextToken(FloydLexer.CLOSEPAREN);
		assertNextToken(FloydLexer.IS);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "i");
		assertNextToken(FloydLexer.COLON);
		assertNextToken(FloydLexer.INT);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.BEGIN);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "print");
		assertNextToken(FloydLexer.OPENPAREN);
		assertNextToken(FloydLexer.STRINGLITERAL, "\"Hey,\\\"Sue!\\\"\"");
		assertNextToken(FloydLexer.CONCAT);
		assertNextToken(FloydLexer.IDENTIFIER, "name");
		assertNextToken(FloydLexer.CLOSEPAREN);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.END);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.UNRECOGNIZEDCHAR, "%");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.END);
		assertNextToken(FloydLexer.UNTERMINATEDSTRINGLITERAL, "\"Unterminated");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.ILLEGALSTRINGLITERAL, "\"Hey\\q\"");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.EOF);


		CharStream newInput = CharStreams.fromStream(
				getClass().getResourceAsStream("unittest.floyd"));
		lex.setInputStream(newInput);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.BOOLEAN);
		assertNextToken(FloydLexer.BEGIN);
		assertNextToken(FloydLexer.CLASS);
		assertNextToken(FloydLexer.ELSE);
		assertNextToken(FloydLexer.END);
		assertNextToken(FloydLexer.FALSE);
		assertNextToken(FloydLexer.FROM);
		assertNextToken(FloydLexer.IF);
		assertNextToken(FloydLexer.INHERITS);
		assertNextToken(FloydLexer.INT);
		assertNextToken(FloydLexer.IS);
		assertNextToken(FloydLexer.LOOP);
		assertNextToken(FloydLexer.ME);
		assertNextToken(FloydLexer.NEW);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.NOT);
		assertNextToken(FloydLexer.NULL);
		assertNextToken(FloydLexer.STRING);
		assertNextToken(FloydLexer.THEN);
		assertNextToken(FloydLexer.TRUE);
		assertNextToken(FloydLexer.WHILE);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.IDENTIFIER, "BEGIN");
		assertNextToken(FloydLexer.IDENTIFIER, "_ident");
		assertNextToken(FloydLexer.IDENTIFIER, "s7j_");
		assertNextToken(FloydLexer.IDENTIFIER, "___");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.INTEGERLITERAL, "3521");
		assertNextToken(FloydLexer.INTEGERLITERAL, "15");
		assertNextToken(FloydLexer.INTEGERLITERAL, "-4068");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.STRINGLITERAL, "\"Hi, \\\"Tom\\\", \\nHow are \\333things\\222 today?\"");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\t\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\n\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\f\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\r\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\\"\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"\\\\\"");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.CONCAT);
		assertNextToken(FloydLexer.PLUS);
		assertNextToken(FloydLexer.MINUS);
		assertNextToken(FloydLexer.TIMES);
		assertNextToken(FloydLexer.DIV);
		assertNextToken(FloydLexer.GT);
		assertNextToken(FloydLexer.GTE);
		assertNextToken(FloydLexer.EQ);
		assertNextToken(FloydLexer.CONCAT);
		assertNextToken(FloydLexer.PLUS);
		assertNextToken(FloydLexer.MINUS);
		assertNextToken(FloydLexer.TIMES);
		assertNextToken(FloydLexer.DIV);
		assertNextToken(FloydLexer.GT);
		assertNextToken(FloydLexer.GTE);
		assertNextToken(FloydLexer.EQ);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.ASSIGNMENT);
		assertNextToken(FloydLexer.OPENPAREN);
		assertNextToken(FloydLexer.CLOSEPAREN);
		assertNextToken(FloydLexer.OPENBRACKET);
		assertNextToken(FloydLexer.CLOSEBRACKET);
		assertNextToken(FloydLexer.COMMA);
		assertNextToken(FloydLexer.SEMICOLON);
		assertNextToken(FloydLexer.COLON);
		assertNextToken(FloydLexer.PERIOD);
		assertNextToken(FloydLexer.ASSIGNMENT);
		assertNextToken(FloydLexer.OPENPAREN);
		assertNextToken(FloydLexer.CLOSEPAREN);
		assertNextToken(FloydLexer.OPENBRACKET);
		assertNextToken(FloydLexer.CLOSEBRACKET);
		assertNextToken(FloydLexer.COMMA);
		assertNextToken(FloydLexer.SEMICOLON);
		assertNextToken(FloydLexer.COLON);
		assertNextToken(FloydLexer.PERIOD);
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.STRINGLITERAL, "\"hello\"");
		assertNextToken(FloydLexer.STRINGLITERAL, "\"goodbye\"");
		assertNextToken(FloydLexer.NEWLINE);
		assertNextToken(FloydLexer.EOF);
	}

	private void assertNextToken(int type, String value) throws IOException {
		Token tok = lex.nextToken();
		System.err.println(tok.getLine() + ":" + tok.getCharPositionInLine() + ":" + tok.getText());
		assertTrue(tok.getType() == type);
		assertTrue(tok.getText().equals(value));

	}

	private void assertNextToken(int type) throws IOException {
		Token tok = lex.nextToken();
		System.err.println(tok.getLine() + ":" + tok.getCharPositionInLine() + ":" + tok.getText());
		assertTrue(tok.getType() == type);
	}
}
