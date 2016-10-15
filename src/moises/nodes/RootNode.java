package moises.nodes;

import moises.nodes.BlockNode;
import moises.nodes.Node;

import java.util.List;
import java.util.LinkedList;

public class RootNode extends BlockNode {
    public RootNode(BlockNode block, List <Node> inlineFunctions) {
        super(block.getStatements());
        for (Node inlineFunction: inlineFunctions) {
            ((LinkedList) getStatements()).addFirst(inlineFunction);
        }
    }
}