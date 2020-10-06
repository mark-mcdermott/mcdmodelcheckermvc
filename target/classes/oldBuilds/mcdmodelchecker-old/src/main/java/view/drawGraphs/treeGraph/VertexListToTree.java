package view.drawGraphs.treeGraph;

import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Vertex;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

public class VertexListToTree {

    DelegateTree<Vertex, Integer> delegateTree;

    public DelegateTree<Vertex, Integer> VertexListToTree (VertexList vertexList) {
        delegateTree = new DelegateTree<>(new DirectedSparseMultigraph<>());
        delegateTree.addVertex(vertexList.getRoot());
        return buildTreeRecursively(vertexList.getRoot());
    }

    private DelegateTree<Vertex, Integer> buildTreeRecursively (Vertex parent) {
        if (parent.getChildren() != null) {
            for (Vertex child : parent.getChildren()) {
                delegateTree.addChild(child.getNumber(), parent, child);
                buildTreeRecursively(child);
            }
        }
        return delegateTree;
    }

}
