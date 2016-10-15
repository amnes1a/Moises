package moises.node.command;

import moises.core.Parser;
import moises.node.Node;


/**
 * Created by arturozamora on 10/15/16.
 */
public class PowCommand extends Node{
    public Parser parser;
    public PowCommand() {}
    public PowCommand(Parser parser) {
        this.parser = parser;
    }

    public Object eval() {
        Double base = (Double) parser.getVariable("base");
        Double exponent = (Double) parser.getVariable("exponent");
        return Math.pow(base, exponent);
    }
}
