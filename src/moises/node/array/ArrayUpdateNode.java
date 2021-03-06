package moises.node.array;

import moises.node.Node;

import java.util.List;
public class ArrayUpdateNode extends Node {
    private Node array;
    private Node indexExpression;
    private Node rightSideExpression;

    public ArrayUpdateNode(Node array, Node indexExpression, Node rightSideExpression) {
        this.array = array;
        this.indexExpression = indexExpression;
        this.rightSideExpression = rightSideExpression;
    }

    public int toDouble(Node node) {
        Object res = node.eval();
        return ((Integer) res).intValue();
    }
    
    public Object eval() {
        Object arrayVariable = array.eval();
        int index = toDouble(indexExpression);
        Object newValue = rightSideExpression.eval();
        Object ret = ((List) arrayVariable).set(index, newValue);
        return ret;
    }
}