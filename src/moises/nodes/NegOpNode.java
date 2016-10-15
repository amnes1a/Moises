package moises.nodes;

import moises.nodes.Node;

public class NegOpNode extends Node {
    public Node node;
    public NegOpNode() {}

    public NegOpNode(Node node) {
        this.node = node;
    }

    public int ToInt(Node node) {
        Object res = node.eval();
        return ((Integer) res).intValue();
    }
    
    public Object eval() {
        Object result = new Integer(-ToInt(node));
        return result;
    }
}