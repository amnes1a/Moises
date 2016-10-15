package moises.nodes;

import moises.nodes.Node;

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
