package moises.node;

import moises.tokenizer.TokenType;

public class BinOpNode extends Node {
    public TokenType op;
    public Node left;
    public Node right;
    public BinOpNode() {}
    public BinOpNode(TokenType op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
    public double toDouble(Node node) {
        Object res = node.eval();
        return ((Double) res).doubleValue();
    }
    public boolean toBoolean(Node node) {
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }
    public Object ToObject(Node node) {
        return node.eval();
    }
    public Object eval() {
        Object result = null;
        switch (op) {
            case ADD:
                result = new Double(toDouble(left) + toDouble(right));
                break;
            case SUBTRACT:
                result = new Double(toDouble(left) - toDouble(right));
                break;
            case MULTIPLY:
                result = new Double(toDouble(left) * toDouble(right));
                break;
            case DIVIDE:
                if (toDouble(right) == 0) {
                    System.out.println("Error: Division by Zero!");
                    System.exit(0);
                }
                result = new Double(toDouble(left) / toDouble(right));
                break;
            case MOD:
                result = new Double(toDouble(left) % toDouble(right));
                break;
            case LESS:
                result = new Boolean(toDouble(left) < toDouble(right));
                break;
            case GREATER:
                result = new Boolean(toDouble(left) > toDouble(right));
                break;
                // != and == work as equal and !equal for strings
            case EQUAL:
                result = new Boolean(ToObject(left).equals(ToObject(right)));
                break;
            case NOTEQUAL:
                result = new Boolean(!ToObject(left).equals(ToObject(right)));
                break;
            case LESSEQUAL:
                result = new Boolean(toDouble(left) <= toDouble(right));
                break;
            case GREATEREQUAL:
                result = new Boolean(toDouble(left) >= toDouble(right));
                break;
            case OR:
                result = new Boolean(toBoolean(left) || toBoolean(right));
                break;
            case AND:
                result = new Boolean(toBoolean(left) && toBoolean(right));
                break;
        }
        return result;
    }
}
