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

/**
 * Create negative node to implement negative numbers.
 */
public class NegOpNode extends Node {
    /**
     * 
     */
    public Node node;
    /**
     * 
     */
    public NegOpNode() {}

    /**
     * 
     * @param node 
     */
    public NegOpNode(Node node) {
        this.node = node;
    }

    /**
     * Convert node to double.
     * @param node
     * @return 
     */
    public double toDouble(Node node) {
        Object res = node.eval();
        return ((Double) res).doubleValue();
    }
    
    /**
     * 
     * @return The current node in negative.
     */
    public Object eval() {
        Object result = new Double(-toDouble(node));
        return result;
    }
}