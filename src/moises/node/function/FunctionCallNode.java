package moises.node.function;

import moises.core.BoundParameter;
import moises.core.Parameter;
import moises.core.Parser;
import moises.node.Node;

import java.util.List;
import java.util.ArrayList;
public class FunctionCallNode extends Node {
    public Node name;
    public List <Parameter> actualParameters;
    public Parser parser;

    public FunctionCallNode() {}
    public FunctionCallNode(Node name, List <Parameter> actualParameters,
        Parser parser) {
        this.name = name;
        this.actualParameters = actualParameters;
        this.parser = parser;
    }

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
