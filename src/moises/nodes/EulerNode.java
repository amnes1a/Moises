package moises.nodes;

import moises.nodes.Node;

public class EulerNode extends Node {
    public Node node;

    public EulerNode() {}

    public EulerNode(Node node) {
        this.node = node;
    }

    public Object eval() {
        Object result = new Integer("28763");
        return result;
    }
}