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
package moises.node.function;

import moises.core.Parameter;
import moises.node.Node;
import java.util.List;

/**
 * Class extending node to implement functions in the linked list and create
 * scopes for them.
 */
public class Function extends Node {
    /**
     * Body of the function.
     */
    private Node body;
    /**
     * List of the parameters for the function.
     */
    private List <Parameter> parameters;
    /**
     * Name of the function.
     */
    private String name;
    
    /**
     * 
     * @param name
     * @param body
     * @param parameters 
     */
    public Function(String name, Node body, List <Parameter> parameters) {
        this.body = body;
        this.parameters = parameters;
        this.name = name;
    }
    /**
     * 
     * @return 
     */
    public Object eval() {
        return body.eval();
    }
    /**
     * 
     * @return 
     */
    public List <Parameter> getParameters() {
        return parameters;
    }
    /**
     * 
     * @return 
     */
    public Node getBody() {
        return body;
    }
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
}