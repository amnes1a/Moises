package moises.nodes;

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
    public int ToInt(Node node) {
        Object res = node.eval();
        return ((Integer) res).intValue();
    }
    public boolean ToBoolean(Node node) {
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
                result = new Integer(ToInt(left) + ToInt(right));
                break;
            case SUBTRACT:
                result = new Integer(ToInt(left) - ToInt(right));
                break;
            case MULTIPLY:
                result = new Integer(ToInt(left) * ToInt(right));
                break;
            case DIVIDE:
                if (ToInt(right) == 0) {
                    System.out.println("Error: Division by Zero!");
                    System.exit(0);
                }
                result = new Integer(ToInt(left) / ToInt(right));
                break;
            case LESS:
                result = new Boolean(ToInt(left) < ToInt(right));
                break;
            case GREATER:
                result = new Boolean(ToInt(left) > ToInt(right));
                break;
                // != and == work as equal and !equal for strings
            case EQUAL:
                result = new Boolean(ToObject(left).equals(ToObject(right)));
                break;
            case NOTEQUAL:
                result = new Boolean(!ToObject(left).equals(ToObject(right)));
                break;
            case LESSEQUAL:
                result = new Boolean(ToInt(left) <= ToInt(right));
                break;
            case GREATEREQUAL:
                result = new Boolean(ToInt(left) >= ToInt(right));
                break;
            case OR:
                result = new Boolean(ToBoolean(left) || ToBoolean(right));
                break;
            case AND:
                result = new Boolean(ToBoolean(left) && ToBoolean(right));
                break;
        }
        return result;
    }
}
