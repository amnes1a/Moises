package moises.nodes;

import moises.nodes.Node;

public class NotOpNode extends Node {
    public Node node;
    public NotOpNode() {}
    public NotOpNode(Node node) {
        this.node = node;
    }
    public boolean ToBoolean(Node node) {
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }
    public Object eval() {
        Object result = new Boolean(!ToBoolean(node));
        return result;
    }
}