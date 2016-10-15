package moises.nodes;

import moises.nodes.Node;

public class PiNode extends Node {
    public Node node;

    public PiNode() {}

    public PiNode(Node node) {
        this.node = node;
    }

    public Object eval() {
        Object result = new Integer("31516");
        return result;
    }
}