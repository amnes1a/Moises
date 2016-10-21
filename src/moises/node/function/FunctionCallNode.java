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

import moises.core.BoundParameter;
import moises.core.Parameter;
import moises.core.Parser;
import moises.node.Node;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is to call a function and implement what it needs to do.
 */
public class FunctionCallNode extends Node {
    /**
     * Name of the function.
     */
    public Node name;
    /**
     * Parameters of the function.
     */
    public List <Parameter> actualParameters;
    /**
     * 
     */
    public Parser parser;

    /**
     * 
     */
    public FunctionCallNode() {}
    
    /**
     * 
     * @param name
     * @param actualParameters
     * @param parser 
     */
    public FunctionCallNode(Node name, List <Parameter> actualParameters,
        Parser parser) {
        this.name = name;
        this.actualParameters = actualParameters;
        this.parser = parser;
    }

    /**
     * 
     * @return 
     */
    public Object eval() {
        // Get the function object from symbol table 
        // with this name by evaluating variable node 
        Function function = (Function) name.eval();
        List <BoundParameter> boundParameters = new ArrayList();
        if (function.getParameters() != null) {
            // Some functions do not have parameters, check it out
            if (actualParameters != null) {
                if (actualParameters.size() < function.getParameters().size()) {
                    System.out.println("Too Few Parameters in Function Call: " +

                        function.getName());
                    System.exit(0);
                } else if (actualParameters.size() > function.getParameters().size()) {
                    System.out.println("Too Many Parameters in Function Call: " +

                        function.getName());
                    System.exit(0);
                } else {
                    // Bind actual parameters
                    for (int index = 0; index < actualParameters.size(); index++) {
                        String name = function.getParameters().get(index).getName();
                        Object value = actualParameters.get(index).getValue();
                        //If the parameter is a function!
                        if (value instanceof Function) {
                            value = ((Function) value).eval();
                        }
                        boundParameters.add(new BoundParameter(name, value));
                    }
                }
            }
        } //Now, call this function
        return parser.ExecuteFunction(function, boundParameters);
    }
}
