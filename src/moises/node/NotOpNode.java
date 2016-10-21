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
 * Change the boolean status to any node.
 */
public class NotOpNode extends Node {
    /**
     * 
     */
    public Node node;
    /**
     * 
     */
    public NotOpNode() {}
    /**
     * 
     * @param node 
     */
    public NotOpNode(Node node) {
        this.node = node;
    }
    /**
     * Convert node to boolean.
     * @param node
     * @return  The converted node.
     */
    public boolean ToBoolean(Node node) {
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }
    /**
     * 
     * @return 
     */
    public Object eval() {
        Object result = new Boolean(!ToBoolean(node));
        return result;
    }
}