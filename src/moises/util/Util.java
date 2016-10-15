package moises.util;

import moises.Parameter;
import moises.Parser;
import moises.nodes.*;
import moises.tokenizer.Token;
import moises.tokenizer.TokenType;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Util {

    public static List<Node> CreateInlineFunctions(Parser parser) {
        List<Node> inlineFunctions = new LinkedList<Node>();
        inlineFunctions.add(CreatePrintFunction(parser));
        inlineFunctions.add(CreatePrintlnFunction(parser));
        inlineFunctions.add(CreateWaitFunction(parser));
        inlineFunctions.add(CreateArraySizeFunction(parser));
        inlineFunctions.add(CreateStrLenFunction(parser));
        inlineFunctions.add(CreateStrConcatFunction(parser));
        inlineFunctions.add(CreateInputFunction(parser));
        return inlineFunctions; 
    }

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


    public static void Write(Object obj) {
        System.out.print(obj);
    }

    public static void Writeln(Object obj) {
        System.out.println(obj);
    }

    public static void Writeln() {
        System.out.println();
    }
    
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
