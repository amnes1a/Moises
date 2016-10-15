package moises.node.controlstatement;

import moises.core.Parser;
import moises.node.Node;
import moises.node.function.Function;

public class AssignmentNode extends Node {
    public String name;
    public Node value;
    public Parser parser;

    public AssignmentNode() {}

    public AssignmentNode(String name, Node value, Parser parser) {
        this.name = name;
        this.value = value;
        this.parser = parser;
    }
    public Object eval() {
        if (value instanceof Function)
            return parser.setVariable(name, value);
        else
            return parser.setVariable(name, value.eval());
    }
}