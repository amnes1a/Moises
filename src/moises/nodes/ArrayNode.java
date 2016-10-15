package moises.nodes;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends Node {
    private List <Node> elements;
    public ArrayNode(List <Node> elements) {
        this.elements = elements;
    }
    public Object eval() {
        List < Object > items = new ArrayList < Object > (elements.size());
        for (Node node: elements) {
            items.add(node.eval());
        }
        return items;
    }
}