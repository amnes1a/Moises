package moises.node.command;

import moises.core.Parser;
import moises.node.Node;

import java.util.Scanner;

public class InputCommand extends Node {
    public Parser parser;

    public InputCommand() {}

    public InputCommand(Parser parser) {
        this.parser = parser;
    }

    public Object eval() {
        Scanner scanner = new Scanner(System.in);
        String message = (String) parser.getVariable("inputMessage");
        System.out.print(message);
        String result = scanner.nextLine();
        return result;
    }
}