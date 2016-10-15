package moises.node.datatype;

import moises.node.Node;

public class StringNode extends Node {
    String text;
    public StringNode() {}
    public StringNode(String text) {
        this.text = text;
    }
    public Object eval() {
        return text;
    }
}
