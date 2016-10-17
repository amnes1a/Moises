/*
 * Copyright (C) 2016 Moises Language
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package moises.core;

import moises.node.*;
import moises.node.array.ArrayNode;
import moises.node.array.ArrayUpdateNode;
import moises.node.constant.EulerNode;
import moises.node.constant.PiNode;
import moises.node.controlstatement.AssignmentNode;
import moises.node.controlstatement.IfNode;
import moises.node.controlstatement.WhileNode;
import moises.node.datatype.NumberNode;
import moises.node.datatype.StringNode;
import moises.node.datatype.VariableNode;
import moises.node.function.Function;
import moises.node.function.FunctionCallNode;
import moises.tokenizer.Token;
import moises.tokenizer.TokenType;
import moises.util.Util;

import java.util.ArrayList;
import java.util.*;

/**
 * This defines the Moises parser. The parser takes in a sequence of tokens
 * and generates an abstract syntax tree. This is the nested data structure
 * that represents the series of statements, and the expressions (which can
 * nest arbitrarily deeply) that they evaluate. In technical terms, what we
 * have is a recursive descent parser, the simplest kind to hand-write.
 *
 */
public class Parser {
    /**
     * Current position of the tokenizer.
     */
    public  int currentTokenPosition = 0; 
    /**
     * List of tokens that where found.
     */
    public  List<Token> tokens;
    /**
     * Hashmap of the symbol table.
     */
    public Map symbolTable = new HashMap();

    /**
     * Empty Constructor.
     */
    public Parser() {}

    /**
     * Constructor with a list of tokens predefined.
     * @param tokens 
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens; 
    }

    /**
     * 
     * @return 
     */
    public List<Token> getTokens() {
        return tokens; 
    }

    /**
     * This function parses different statements so we can create different
     * scopes inside Moises. This will save each statement in a different node.
     * @return The parsed node.
     */
    public Node Statement() {
        Node node = null;
        TokenType type = CurrentToken().type;

        if (IsAssignment()) {
            node = Assignment();
        } else if (IsWhile()) {
             node = While();
        } else if (IsIfElse()) {
             node = If(); 
        } else if (IsArrayAccess()) {
             node = ArrayUpdate();
        } else if (IsFunctionDef()) {
             node = FunctionDefinition();
        } else if (IsFunctionCall()) {
             node = FunctionCall();
        } else {

            Util.Writeln("Unknown language construct: " +
                CurrentToken().text);
            System.exit(0);

        }
        return node;
    }

    /**
     * Gets an unconsumed token, indexing forward. get(0) will be the next
     * token to be consumed, get(1) the one after that, etc.
     * 
     * @param  offset How far forward in the token stream to look.
     * @return        The yet-to-be-consumed token.
     */
    public Token GetToken(int offset) {
        if (currentTokenPosition + offset >= tokens.size()) {   
            return new Token("", TokenType.EOF);
        }
        return tokens.get(currentTokenPosition + offset);
    }

    /**
     * Return Current Token.
     * @return 
     */
    public Token CurrentToken() {
        return GetToken(0);
    }

    /**
     * Return next token in the list.
     * @return 
     */
    public Token NextToken() {
        return GetToken(1); 
    }

    /**
     * Consume current token.
     * @param offset 
     */
    public void EatToken(int offset) {
        currentTokenPosition = currentTokenPosition + offset;
    }

    /**
     * Check token and consume it.
     * @param type
     * @return 
     */
    public Token MatchAndEat(TokenType type) {
        Token token = CurrentToken();
        if (!CurrentToken().type.equals(type)) 
            System.out.println("Saw " + token.type + " but " + type + " expected");
        EatToken(1);
        return token;
    }

    /**
     * Check and consume token if is a multiply operator.
     * @return 
     */
    public Node Multiply() {
        MatchAndEat(TokenType.MULTIPLY);
        return Factor();
    }

    /**
     * Check and consume token if is a divisor operator.
     * @return 
     */
    public Node Divide() {
        MatchAndEat(TokenType.DIVIDE);
        return Factor();
    }

    /**
     * Check and consume token if is a mod operator.
     * @return 
     */
    public Node Mod() {
        MatchAndEat(TokenType.MOD);
        return Factor();
    }

    /**
     * Check and consume token if is a sum operator.
     * @return 
     */
    public Node Add() {
        MatchAndEat(TokenType.ADD);
        return Term();
    }

    /**
     * Check and consume token if is a sub operator.
     * @return 
     */
    public Node Subtract() {
        MatchAndEat(TokenType.SUBTRACT);
        return Term();
    }

    /**
     * Parses an "atomic" expression. This is the highest level of
     * precedence and contains single literal tokens like 123 and "foo", as
     * well as parenthesized expressions.
     * 
     * @return The parsed node.
     */
    public Node Factor() {
        Node result = null;
        if (CurrentToken().type == TokenType.LEFT_PAREN) {
            /* The contents of a parenthesized expression can be any
               expression. This lets us "restart" the precedence cascade
               so that you can have a lower precedence expression inside
               the parentheses.
            */
            MatchAndEat(TokenType.LEFT_PAREN);
            result = Expression();
            MatchAndEat(TokenType.RIGHT_PAREN);
        } else if (IsNumber()) {
            Token token = MatchAndEat(TokenType.NUMBER);
            result = new NumberNode(new Double(token.text).doubleValue());
        } else if (IsString()) {
            Token token = MatchAndEat(TokenType.STRING);
            result = new StringNode(new String(token.text));
        } else if(IsConstant()){
            Token token = MatchAndEat(CurrentToken().type);
            switch (token.getType()){
                case PI:
                    result = new PiNode();
                    break;
                case EULER:
                    result = new EulerNode();
            }
        }else if (IsKeyWord()) {
            result = Variable();
        }
        return result;
    }

    /**
     * 
     * @return 
     */
    public boolean IsFunctionDef() {
        TokenType type = CurrentToken().type;
        return type == TokenType.DEF && NextToken().type == TokenType.KEYWORD; 
    }

    /**
     * 
     * @return 
     */
    public boolean IsFunctionCall() {
        TokenType type = CurrentToken().type;
        return type == TokenType.KEYWORD && NextToken().type == TokenType.LEFT_PAREN;
    }

    /**
     * 
     * @return 
     */
    public boolean IsArrayAccess() {
        TokenType type = CurrentToken().type; 
        return type == TokenType.KEYWORD &&
                        NextToken().type == TokenType.LEFT_BRACKET;
    }

    /**
     * 
     * @return 
     */
    public boolean IsConstant(){
        return CurrentToken().type == TokenType.PI || 
               CurrentToken().type == TokenType.EULER; 
    }

    /**
     * 
     * @return 
     */
    public boolean IsString() {
        return CurrentToken().type == TokenType.STRING; 
    }

    /**
     * 
     * @return 
     */
    public boolean IsKeyWord() {
        return CurrentToken().type == TokenType.KEYWORD; 
    }

    /**
     * 
     * @param name
     * @param value
     * @return 
     */
    public Object setVariable(String name, Object value) {
        symbolTable.put(name, value);
        return value;
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public Object getVariable(String name) {
            Object value = (Object) symbolTable.get(name);
            if (value != null) return value;
            return null;
        }

    /**
     * An assignment of an expression to a keyword.
     * @return 
     */
    public Node Assignment() {
        Node node = null;
        String name = MatchAndEat(TokenType.KEYWORD).text;
        MatchAndEat(TokenType.ASSIGNMENT);
        if (CurrentToken().type == TokenType.LEFT_BRACKET) {
            node = ArrayDefinition(name);
        } else {
            Node value = Expression();
            node = new AssignmentNode(name, value, this);
        }
        return node;
    }

    /**
     * This will define a function, creating different meta data that the
     * interpreter will use to create the function.
     * @return 
     */
    public Node FunctionDefinition() {
        MatchAndEat(TokenType.DEF);
        String functionName = MatchAndEat(TokenType.KEYWORD).text;

        MatchAndEat(TokenType.LEFT_PAREN);
        List <Parameter> parameters = FunctionDefParameters();
        MatchAndEat(TokenType.RIGHT_PAREN);

        Node functionBody = Block();
        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, this);

        return functionVariable;
    }

    /**
     * This will help the parser to define an array.
     * @param name
     * @return 
     */
    public Node ArrayDefinition(String name) {
        List < Node > elements = new ArrayList < Node > ();
        MatchAndEat(TokenType.LEFT_BRACKET);
        if (CurrentToken().type != TokenType.RIGHT_BRACKET) {
            elements.add(Expression());
            while (CurrentToken().type != TokenType.RIGHT_BRACKET) {
                MatchAndEat(TokenType.COMMA);
                elements.add(Expression());
            }
        }
        MatchAndEat(TokenType.RIGHT_BRACKET);
        return new AssignmentNode(name, new ArrayNode(elements), this);
    }

    /**
     * Defining a variable for the interpreter.
     * @return 
     */
    public Node Variable() {
        Node node = null;
        if (NextToken().type == TokenType.LEFT_PAREN) {
            node = FunctionCall();
        } else {
            Token token = MatchAndEat(TokenType.KEYWORD);
            Node varNode = new VariableNode(token.text, this);
            // Handle array access here
            if (CurrentToken().type == TokenType.LEFT_BRACKET) {
                MatchAndEat(TokenType.LEFT_BRACKET);
                Node key = Expression();
                MatchAndEat(TokenType.RIGHT_BRACKET);
                return new LookupNode((VariableNode) varNode, key);
            } else return varNode;
        }
        return node;
    }

    /**
     * Calling the function when the code is run.
     * @return 
     */
    public Node FunctionCall() {
        String functionName = MatchAndEat(TokenType.KEYWORD).text;
        Node calleeFunctionName = new VariableNode(functionName, this);
        MatchAndEat(TokenType.LEFT_PAREN);
        List <Parameter> actualParameters = FunctionCallParameters();
        MatchAndEat(TokenType.RIGHT_PAREN);
        Node functionCallNode = new FunctionCallNode(calleeFunctionName, actualParameters, this);
        return functionCallNode;
    }

    /**
     * Calling the function, with its parameters, when the code is run.
     * @return 
     */
    public List FunctionCallParameters() {
        List <Parameter> actualParameters = null;
        Node expression = Expression();
        if (expression != null) {
            actualParameters = new ArrayList();
            actualParameters.add(new Parameter(expression));
            while (CurrentToken().type == TokenType.COMMA) {
                MatchAndEat(TokenType.COMMA);
                actualParameters.add(new Parameter(Expression()));
            }
        }
        return actualParameters;
    }

    /**
     * Check for different type of arithmetic signs that can be used as a binary
     * operators.
     * @return The final binary node.
     */
    public Node Term() {
        Node node = SignedFactor();
        while (IsMulOp(CurrentToken().type)) {
            switch (CurrentToken().type) {
                case MULTIPLY:
                    node = new BinOpNode(TokenType.MULTIPLY, node, Multiply());
                    break;
                case DIVIDE:
                    node = new BinOpNode(TokenType.DIVIDE, node, Divide());
                    break;
                case MOD:
                    node = new BinOpNode(TokenType.MOD, node, Mod());
                    break;
            }
        }
        return node;
    }    

    /**
     * 
     * @return 
     */
    public Node SignedFactor() {
        if (CurrentToken().type == TokenType.SUBTRACT) {
            MatchAndEat(TokenType.SUBTRACT);
            Node node = new NegOpNode(Factor());
            return node;
        }
        return Factor();
    }

    /**
     * Basic arithmetic expressions for the interpreter
     * @return 
     */
    public Node ArithmeticExpression() {
        Node node = Term();
        while (IsAddOp(CurrentToken().type)) {
            switch (CurrentToken().type) {
                case ADD:
                    node = new BinOpNode(TokenType.ADD, node, Add());
                    break;
                case SUBTRACT:
                    node = new BinOpNode(TokenType.SUBTRACT, node, Subtract());
                    break;
            }
        }
        return node;
    }

    /**
     * Function to update or change an array predefined.
     * @return 
     */
    public Node ArrayUpdate() {
        String arrayName = MatchAndEat(TokenType.KEYWORD).text;
        Node array = new VariableNode(arrayName, this);
        MatchAndEat(TokenType.LEFT_BRACKET);
        Node indexExpr = Expression();
        MatchAndEat(TokenType.RIGHT_BRACKET);
        MatchAndEat(TokenType.ASSIGNMENT);
        Node rightSideExpr = Expression();
        return new ArrayUpdateNode(array, indexExpr, rightSideExpr);
    }

    /**
     * Logic arithmetic operations that will be user by the parser to
     * identify this on the script.
     * @return 
     */
    public Node Relation() {
        Node node = ArithmeticExpression();
        if (IsRelOp(CurrentToken().type)) {
            switch (CurrentToken().type) {
    
                case LESS:
                    node = Less(node);
                    break;
                case GREATER:
                    node = Greater(node);
                    break;
                case EQUAL:
                    node = Equal(node);
                    break;
                case NOTEQUAL:
                    node = NotEqual(node);
                    break;
                case LESSEQUAL:
                    node = LessEqual(node);
                    break;
                case GREATEREQUAL:
                    node = GreaterEqual(node);
                    break;
            }
        }
        return node;
    }

    /**
     * Create less logic operator.
     * @param node
     * @return 
     */
    public Node Less(Node node) {
        MatchAndEat(TokenType.LESS);
        return new BinOpNode(TokenType.LESS, node, ArithmeticExpression());
    }

    /**
     * Create greater logic operator.
     * @param node
     * @return 
     */
    public Node Greater(Node node) {
        MatchAndEat(TokenType.GREATER);
        return new BinOpNode(TokenType.GREATER, node, ArithmeticExpression());
    }

    /**
     * Create equal logic operator.
     * @param node
     * @return 
     */
    public Node Equal(Node node) {
        MatchAndEat(TokenType.EQUAL);
        return new BinOpNode(TokenType.EQUAL, node, ArithmeticExpression());
    }

    /**
     * Create not equal logic operator.
     * @param node
     * @return 
     */
    public Node NotEqual(Node node) {
        MatchAndEat(TokenType.NOTEQUAL);
        return new BinOpNode(TokenType.NOTEQUAL, node, ArithmeticExpression());
    }

    /**
     * Create less or equal logic operator.
     * @param node
     * @return 
     */
    public Node LessEqual(Node node) {
        MatchAndEat(TokenType.LESSEQUAL);
        return new BinOpNode(TokenType.LESSEQUAL, node, ArithmeticExpression());
    }

    /**
     * Create greater or equal logic operator.
     * @param node
     * @return 
     */
    public Node GreaterEqual(Node node) {
        MatchAndEat(TokenType.GREATEREQUAL);
        return new BinOpNode(TokenType.GREATEREQUAL, node, ArithmeticExpression());
    }

    /**
     * Create while control statement and creating its own scope.
     * @return 
     */
    public Node While() {
        Node condition, body;
        MatchAndEat(TokenType.WHILE);
        condition = Expression();
        body = Block();
        return new WhileNode(condition, body);
    }

    /**
     * Create an if-else control statement and creating an scope for this control
     * statement.
     * @return 
     */
    public Node If() {
        Node condition = null, thenPart = null, elsePart = null;
        MatchAndEat(TokenType.IF);
        condition = Expression();
        thenPart = Block();
        if (CurrentToken().type == TokenType.ELSE) {
            MatchAndEat(TokenType.ELSE);
            if (CurrentToken().type == TokenType.IF) 
                elsePart = If();
            else 
                elsePart = Block();
        }
        return new IfNode(condition, thenPart, elsePart);
    }

    /**
     * 
     * @return 
     */
    public Node BooleanFactor() {
        return Relation();
    }

    /**
     * 
     * @return 
     */
    public Node BooleanTerm() {
        Node node = NotFactor();
        while (CurrentToken().type == TokenType.AND) {
            MatchAndEat(TokenType.AND);
            node = new BinOpNode(TokenType.AND, node, NotFactor());
        }
        return node;
    }

    /**
     * 
     * @return 
     */
    public Node NotFactor() {
        if (CurrentToken().type == TokenType.NOT) {
            MatchAndEat(TokenType.NOT);
            Node node = BooleanFactor();
            return new NotOpNode(node);
        }
        return BooleanFactor();
    }

    /**
     * 
     * @return 
     */
    public Node BooleanExpression() {
        Node node = BooleanTerm();
        while (IsLogicalOp(CurrentToken().type)) {
            switch (CurrentToken().type) {
                case OR:
                    MatchAndEat(TokenType.OR);
                    node = new BinOpNode(TokenType.OR, node, BooleanTerm());
                    break;
            }
        }
        return node;
    }

    /**
     * 
     * @return A boolean expression.
     */
    public Node Expression() {
        return BooleanExpression(); 
    }

    /**
     * Check if is a medium arithmetic operator
     * @param type
     * @return 
     */
    public boolean IsMulOp(TokenType type) {
        return type == TokenType.MULTIPLY || type == TokenType.DIVIDE || type == TokenType.MOD;
    }

    /**
     * Check if is a basic arithmetic operator.
     * @param type
     * @return 
     */
    public boolean IsAddOp(TokenType type) {
        return type == TokenType.ADD || type == TokenType.SUBTRACT;
    }

    /**
     * Check if is a medium logic operator.
     * @param type
     * @return 
     */
    public boolean IsMultiDigitOp(TokenType type) {
        return type == TokenType.LESSEQUAL || type == TokenType.GREATEREQUAL;
    }

    /**
     * Check if is a basic logic operator.
     * @param type
     * @return 
     */
    public boolean IsRelOp(TokenType type) {
        boolean lgOps = type == TokenType.LESS || type == TokenType.GREATER;
        boolean eqOps = type == TokenType.EQUAL || type == TokenType.NOTEQUAL;
        return eqOps || lgOps || IsMultiDigitOp(type);
    }

    /**
     * Check if is a and-or operator.
     * @param type
     * @return 
     */
    public boolean IsLogicalOp(TokenType type) {
        return type == TokenType.OR || type == TokenType.AND;
    }

    /**
     * 
     * @return 
     */
    public boolean IsNumber() {
        return CurrentToken().type == TokenType.NUMBER;
    }

    /**
     * 
     * @return 
     */
    public boolean IsAssignment() {
        TokenType type = CurrentToken().type; 
        return type == TokenType.KEYWORD &&
                       NextToken().type == TokenType.ASSIGNMENT;
    }

    /**
     * 
     * @return 
     */
    public boolean IsWhile() {
        return CurrentToken().type == TokenType.WHILE; 
    }

    /**
     * 
     * @return 
     */
    public boolean IsIfElse() {
        TokenType type = CurrentToken().type;
        return type == TokenType.IF || type == TokenType.ELSE; 
    }

    /**
     * Create a block node to encapsulate other nodes.
     * @return The node block.
     */
    public BlockNode Block() {
        List < Node > statements = new LinkedList < Node > ();
        while (CurrentToken().type != TokenType.END) {
            statements.add(Statement());
        }
        MatchAndEat(TokenType.END);
        return new BlockNode(statements);
    }

    /**
     * Give the parameters to its corresponding function
     * @return The list of parameters from the function
     */
    public List FunctionDefParameters() {
        List <Parameter> parameters = null;
        if (CurrentToken().type == TokenType.KEYWORD) {
            parameters = new ArrayList();
            parameters.add(new Parameter(MatchAndEat(TokenType.KEYWORD).text));
            while (CurrentToken().type == TokenType.COMMA) {
                MatchAndEat(TokenType.COMMA);
                parameters.add(new Parameter(MatchAndEat(TokenType.KEYWORD).text));
            }
        }
        return parameters;
    }
    
    /**
     * 
     * @param function
     * @param boundParameters
     * @return 
     */
    public Object ExecuteFunction(Function
        function, List boundParameters) {
        // Save the symbolTable
        Map < String, Object > savedSymbolTable =
            new HashMap < String, Object > (symbolTable);
        // Get bound parameters
        for (int index = 0; index < boundParameters.size(); index++) {
            BoundParameter param = (BoundParameter) boundParameters.get(index);
            setVariable(param.getName(), param.getValue());
        }
        // Eval function
        Object ret = function.eval(); // Restore symbolTable
        symbolTable = savedSymbolTable;
        return ret;
    }

    /**
     * 
     * @return 
     */
    public RootNode Program() {
        MatchAndEat(TokenType.SCRIPT);
        return new RootNode(Block(), Util.CreateInlineFunctions(this));
    }
}
