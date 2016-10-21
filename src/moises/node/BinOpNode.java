/*
 * Copyright (C) 2016 Moises Language
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package moises.node;

import moises.tokenizer.TokenType;

/**
 * Binary Operation Node Class. This class is to be used in a block linked list to
 * process the nodes.
 * 
 */
public class BinOpNode extends Node {
    /**
     * 
     */
    public TokenType op;
    /**
     * Node to the left.
     */
    public Node left;
    /**
     * Node to the right.
     */
    public Node right;
    /**
     *
     */
    public BinOpNode() {}
    /**
     * 
     * @param op
     * @param left
     * @param right 
     */
    public BinOpNode(TokenType op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
    /**
     * Convert node to double.
     * @param node
     * @return  The parameter converted to double.
     */
    public double toDouble(Node node) {
        Object res = node.eval();
        return ((Double) res).doubleValue();
    }
    /**
     * Convert node to boolean.
     * @param node
     * @return The parameter converted to boolean.
     */
    public boolean toBoolean(Node node) {
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }
    /**
     * Convert node to object.
     * @param node
     * @return  The parameter converted to Object.
     */
    public Object ToObject(Node node) {
        return node.eval();
    }
    /**
     * 
     * @return 
     */
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
