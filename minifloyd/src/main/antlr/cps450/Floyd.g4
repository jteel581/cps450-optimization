// Floyd.g4
// This is the file that holds all of the grammar for the Floyd language.

grammar Floyd;

start 
   : (NEWLINE+)? classes+=class_decl ((NEWLINE+) classes+=class_decl)* (NEWLINE+)? 
   ;

class_decl returns [Type classType, ArrayList<VarDecl> varList, ArrayList<MethodDecl> methodList]
   : CLASS id1=IDENTIFIER (INHERITS FROM id2=IDENTIFIER)? IS (NEWLINE+)
   vars+=var_decl*
   methods+=method_decl*
   END id3=IDENTIFIER
   ;

method_decl returns [Type methodType]
   : id1=IDENTIFIER OPENPAREN (argument_decl_list)? CLOSEPAREN (COLON type)? IS (NEWLINE+)
   (vars+=var_decl)*
   BEGIN (NEWLINE+)
   statement_list
   END id2=IDENTIFIER (NEWLINE+)
   ;

var_decl returns [Type varDeclType]
   : IDENTIFIER (COLON type)? (ASSIGNMENT expression)? (NEWLINE+)
   ;

type returns [Type ttype]
   : (INT | STRING | BOOLEAN)					# RegType
   | IDENTIFIER									# IdType
   | type OPENBRACKET expression? CLOSEBRACKET	# ExprType
   ;

argument_decl_list
   : (args+=argument_decl SEMICOLON)* args+=argument_decl
   ;

argument_decl 
   : IDENTIFIER COLON type
   ;

statement_list
   : ( stmts+=statement (NEWLINE+))*
   ;

statement 
   : assignment_stmt	# AssnStatement
   | if_stmt			# IfStatement
   | loop_stmt			# LoopStatement
   | call_stmt			# CallStatement
   ;

assignment_stmt 
   : IDENTIFIER (OPENBRACKET e1=expression CLOSEBRACKET)* ASSIGNMENT e2=expression
   ;

if_stmt
   : IF expression THEN (NEWLINE+)
   sl1=statement_list
   (ELSE (NEWLINE+) sl2=statement_list)?
   END IF
   ;

loop_stmt
   : LOOP WHILE expression (NEWLINE+)
   statement_list
   END LOOP
   ;



expression_list
   : (exprs+=expression COMMA)*(exprs+=expression) 
   ;
   
   
factor returns [Type factorType]
   : factor AND factoid		# AndFactoidFactor
   | factor AND expression	# AndExpressionFactor
   | factoid				# FactoidFactor
   ;

factoid returns [Type factoidType]
   : n1=nanoterm relational_op n2=nanoterm 	# NanotermFactoid
   | nanoterm relational_op expression		# ExpressionFactoid
   | bbyte									# BbyteFactoid
   ;

bbyte returns [Type bbyteType]
   : bbyte CONCAT nybble		# ConcatNybbleBbyte
   | bbyte CONCAT expression	# ConcatExpressionBbyte
   | nybble						# NybbleBbyte
   ;

nybble returns [Type nybbleType]
   : nybble add_op term			# AdOpTermNybble
   | nybble add_op expression	# AdopExpressionNybble
   | term						# TermNybble
   ;

term returns [Type termType]
   : term mul_op microterm		# MulOpMicrotermTerm
   | term mul_op expression		# MulOPExpressionTerm
   | microterm					# MicrotermTerm
   ;

microterm returns [Type exprType]
   : unary_op (IDENTIFIER | STRINGLITERAL | INTEGERLITERAL | TRUE | FALSE | NULL | ME) (PERIOD)? exprtail	# UnaryOpRegularMicroterm
   | unary_op expression																					# UnaryOpExpressionMicroterm
   | (IDENTIFIER | STRINGLITERAL | INTEGERLITERAL | TRUE | FALSE | NULL | ME) (PERIOD)? exprtail			# RegularMicroterm
   | nanoterm																								# NanotermMicroterm
   ;

nanoterm returns [Type nanoTermType]
   : (IDENTIFIER | STRINGLITERAL | INTEGERLITERAL | TRUE | FALSE | NULL | ME) (PERIOD)? exprtail	# RegularNanoterm	
   | OPENPAREN expression CLOSEPAREN																# ParanNanoterm
   ;   

expression returns [Type exprType]
   : (IDENTIFIER | STRINGLITERAL | INTEGERLITERAL | TRUE | FALSE | NULL | ME) (PERIOD)? exprtail				# NormalExpression
   | NEW type (PERIOD)? exprtail																				# NewTypeExpression
   | expression OR factor (PERIOD)? exprtail																	# OrExpression
   | factor (PERIOD)? exprtail																					# FactorExpression
   | OPENPAREN expression CLOSEPAREN (PERIOD)? exprtail															# ParenthesizedExpression
   | IDENTIFIER OPENBRACKET expression CLOSEBRACKET (OPENBRACKET expression CLOSEBRACKET)* (PERIOD)? exprtail	# BracketExpression
   | exprtail 																									# ExprTailExpression
   ;

exprtail returns [Type exprTailType]
   : IDENTIFIER OPENPAREN (expression_list)? CLOSEPAREN exprtail	# RegularExprTail
   |																# EmptyExprTail
   ;
call_stmt 
   : (expression PERIOD)? IDENTIFIER OPENPAREN (expression_list)? CLOSEPAREN	
   ;


unary_op
   : MINUS	# MinUnaryop
   | PLUS   # PlusUnaryop
   | NOT	# NotUnarop
   ;

add_op
   : PLUS	# PlusAddop
   | MINUS	# MinusAddop
   ;

mul_op
   : TIMES	# TimesMulop
   | DIV	# DivMulop
   ;

relational_op
   : EQ		# EqualRelationalop
   | GT		# GreaterRelationalop
   | GTE	# GreaterOrEqualRelationalop
   ;

binary_op
   : (TIMES | DIV)		
   | (PLUS | MINUS)
   | CONCAT
   | (EQ | GT | GTE)
   | AND
   | OR
   ;

NEWLINE
   : ( ('\u000A') | ('\u000D\u000A') )
   ;

CONTINUATION
   : ( ('_\u000A') | ('_\u000D\u000A') )
   ;

COMMENT
   : '~' ~[\u000A\u000D]* -> skip  
   ;

INTEGERLITERAL
   : '-'? ('0' .. '9')+
   ;

BOOLEAN
   : 'boolean'
   ;

BEGIN
   : 'begin'
   ;

CLASS
   : 'class'
   ;

ELSE
   : 'else'
   ;

END 
   : 'end'
   ;

FALSE
   : 'false'
   ;

FROM
   : 'from'
   ;

IF
   : 'if'
   ;

INHERITS 
   : 'inherits'
   ;

INT 
   : 'int'
   ;

IS
   : 'is'
   ;

LOOP
   : 'loop'
   ;

ME
   : 'me'
   ;

NEW 
   : 'new'
   ;

NOT
   : 'not'
   ;

NULL 
   : 'null'
   ;

STRING
   : 'string'
   ;

THEN
   : 'then'
   ;

TRUE
   : 'true'
   ;

WHILE
   : 'while'
   ;

AND
   : 'and'
   ;

OR 
   : 'or'
   ;

IDENTIFIER
   : VALID_ID_START VALID_ID_CHAR*
   ;

fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;


fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;

STRINGLITERAL
   : '"' VALID_STR_CHAR*? '"'
   ;

UNTERMINATEDSTRINGLITERAL
   : '"' VALID_STR_CHAR*
   ;

ILLEGALSTRINGLITERAL
   : '"' (INVALID_STR_CHAR | VALID_STR_CHAR)*? '"'
   ;

fragment VALID_STR_CHAR
   : ~('\\' | '"' | '\r' | '\n') | '\\t' | '\\n' | '\\f' | '\\r' 
   | '\\"' | '\\''\\' | '\\' ('0' .. '7')('0' .. '7')('0' .. '7')
   ;

fragment INVALID_STR_CHAR
   : [\\"]
   ;


SCIENTIFIC_NUMBER
   : NUMBER (E SIGN? NUMBER)?
   ;

fragment NUMBER
   : ('0' .. '9') + ('.' ('0' .. '9') +)?
   ;


fragment E
   : 'E' | 'e'
   ;


fragment SIGN
   : ('+' | '-')
   ;


OPENPAREN
   : '('
   ;


CLOSEPAREN
   : ')'
   ;

CONCAT
   : '&'
   ;

PLUS
   : '+'
   ;


MINUS
   : '-'
   ;

TIMES
   : '*'
   ;

DIV
   : '/'
   ;

GT
   : '>'
   ;

GTE
   : '>='
   ;

EQ
   : '='
   ;

PERIOD
   : '.'
   ;

POW
   : '^'
   ;

WHITESPACE
   : [ \t] + -> skip
   ;

ASSIGNMENT
   : ':='
   ;

OPENBRACKET
   : '['
   ;

CLOSEBRACKET
   : ']'
   ;

COMMA
   : ','
   ;

SEMICOLON
   : ';'
   ;

COLON
   : ':'
   ;

UNRECOGNIZEDCHAR
   : .
   ;

