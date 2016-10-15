package moises.nodes;

import moises.nodes.Node;

public class IfNode extends Node {
    public Node condition;
    public Node thenPart;
    public Node elsePart;

    public IfNode() {}

    public IfNode(Node condition, Node thenPart, Node elsePart) {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public Object eval() {

        Object ret = null;
        if ((condition != null) && (thenPart != null))
            if (((Boolean) condition.eval()).booleanValue())
                ret = thenPart.eval();
        if ((condition != null) && (elsePart != null))
            if (!((Boolean) condition.eval()).booleanValue())
                ret = elsePart.eval();
        return ret;
    }
    
}
