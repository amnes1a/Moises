package moises.node.constant;

import moises.node.Node;

public class PiNode extends Node {
    public Node node;

    public PiNode() {}

    public PiNode(Node node) {
        this.node = node;
    }

    public Object eval() {
        Object result = new Double("3.141592653589793238");
        return result;
    }
}