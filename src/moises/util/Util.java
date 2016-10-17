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
package moises.util;

import moises.core.Parameter;
import moises.core.Parser;
import moises.node.*;
import moises.node.command.*;
import moises.node.controlstatement.AssignmentNode;
import moises.node.function.Function;
import moises.tokenizer.Token;
import moises.tokenizer.TokenType;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Util class to develop various functions to implement in Moises.
 * 
 */
public class Util {

    /**
     * 
     * @param parser
     * @return 
     */
    public static List<Node> CreateInlineFunctions(Parser parser) {
        List<Node> inlineFunctions = new LinkedList<Node>();
        inlineFunctions.add(CreatePrintFunction(parser));
        inlineFunctions.add(CreatePrintlnFunction(parser));
        inlineFunctions.add(CreateWaitFunction(parser));
        inlineFunctions.add(CreateArraySizeFunction(parser));
        inlineFunctions.add(CreateStrLenFunction(parser));
        inlineFunctions.add(CreateStrConcatFunction(parser));
        inlineFunctions.add(CreateInputFunction(parser));
        inlineFunctions.add(CreatePowFunction(parser));
        return inlineFunctions; 
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreatePrintFunction(Parser parser) {
        String functionName = "print";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("writee"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new PrintCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreateInputFunction(Parser parser) {
        String functionName = "input";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("inputMessage"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new InputCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreatePowFunction(Parser parser) {
        String functionName = "pow";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("base"));
        parameters.add(new Parameter("exponent"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new PowCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreateArraySizeFunction(Parser parser) {
        String functionName = "arraySize";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("array"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new ArraySizeCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreatePrintlnFunction(Parser parser) {
        String functionName = "println";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("writee"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new PrintlnCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreateStrLenFunction(Parser parser) {
        String functionName = "strLen";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("str"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new StrLenCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreateStrConcatFunction(Parser parser) {
        String functionName = "strConcat";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("str1"));
        parameters.add(new Parameter("str2"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new StrConcatCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param parser
     * @return 
     */
    public static Node CreateWaitFunction(Parser parser) {
        String functionName = "wait";
        List <Parameter> parameters = new ArrayList();
        parameters.add(new Parameter("interval"));
        List <Node> statements = new LinkedList <Node> ();
        statements.add(new WaitCommand(parser));
        Node functionBody = new BlockNode(statements);
        Function
        function = new Function(functionName, functionBody, parameters);
        Node functionVariable = new AssignmentNode(functionName, function, parser);
        return functionVariable;
    }

    /**
     * 
     * @param obj 
     */
    public static void Write(Object obj) {
        System.out.print(obj);
    }

    /**
     * 
     * @param obj 
     */
    public static void Writeln(Object obj) {
        System.out.println(obj);
    }

    /**
     * 
     */
    public static void Writeln() {
        System.out.println();
    }
    
    /**
     * 
     * @param tokens 
     */
    public static void PrettyPrint(List <Token> tokens) {
        int numberCount = 0;
        int opCount = 0;
        for (Token token: tokens) {
            if (token.type == TokenType.NUMBER) {
                Writeln("Number....: " + token.text);
                numberCount++;
            } else {
                Writeln("Operator..: " + token.type);
                opCount++;
            }
        }
        Writeln("You have got " +
            numberCount +
            " different number and " +
            opCount +
            " operators.");
    }
}
