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
package moises.core;

import moises.node.Node;

/**
 * This class is to create a node parameter used for the functions in the
 * script.
 * 
 */
public class Parameter {
    /**
     * Name of the bound parameter.
     */
    private String name;
    /**
     * Designed node for the parameter.
     */
    private Node value;
    
    /**
     * 
     * @param value 
     */
    public Parameter(Node value) {
        this.value = value;
    }
    
    /**
     * 
     * @param name
     * @param value 
     */
    public Parameter(String name, Node value) {
        this.name = name;
        this.value = value;
    }
    
    /**
     * 
     * @param name 
     */
    public Parameter(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */
    public Object eval() {
        return value.eval();
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public Object getValue() {
        return value.eval();
    }
}