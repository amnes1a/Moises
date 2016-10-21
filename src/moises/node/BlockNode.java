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

import java.util.List;

/**
 * A node class to create blocks for the master linked list.
 */
public class BlockNode extends Node {
    /**
     * List of nodes.
     */
    private List <Node> statements;

    /**
     * 
     * @param statements 
     */
    public BlockNode(List <Node> statements) {
        this.statements = statements;
    }

    /**
     * 
     * @return 
     */
    public Object eval() {
        Object ret = null;
        for (Node statement: statements) {
            ret = statement.eval();
        }
        return ret;
    }

    /**
     * Get the selected node.
     * @param index
     * @return 
     */
    public Node get(int index) {
        return statements.get(index);
    }

    /**
     * 
     * @return 
     */
    protected List <Node> getStatements() {
        return statements;
    }
    
    /**
     * 
     * @return 
     */
    public String toString() {
        String str = "";
        for (Node statement: statements)
            str = str + statement + "\n";
        return str;
    }
}