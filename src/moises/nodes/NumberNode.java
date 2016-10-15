package moises.nodes;

import moises.nodes.Node;

public class NumberNode extends Node {
    Double value;
    public NumberNode() {}
    public NumberNode(Double value) {
        this.value = value;
    }
    public Object eval() {
        return value;
    }
    public String toString() {
        return value + "";
    }
}
