package moises.nodes;

import moises.nodes.Node;

public class NegOpNode extends Node {
    public Node node;
    public NegOpNode() {}

    public NegOpNode(Node node) {
        this.node = node;
    }

    public double toDouble(Node node) {
        Object res = node.eval();
        return ((Double) res).doubleValue();
    }
    
    public Object eval() {
        Object result = new Double(-toDouble(node));
        return result;
    }
}