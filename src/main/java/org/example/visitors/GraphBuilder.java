package org.example.visitors;

import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.edge.PropertyEdge;
import de.fraunhofer.aisec.cpg.processing.IVisitor;
import org.example.schemas.CustomizedGraph;
import org.jetbrains.annotations.NotNull;

// 构建自定义图结构，主要包括EOG和DFG
public class GraphBuilder extends IVisitor<Node> {
    private CustomizedGraph graph;

    public GraphBuilder() {
        this.graph = new CustomizedGraph();
    }

    public void visit(@NotNull Node t) {
        if (t.getLocation() == null)
            return;
        visitEOG(t);
        visitDFG(t);
    }

    private void visitEOG(Node n) {
        for (PropertyEdge<Node> eogEdge: n.getNextEOGEdges()) {
            Node nextNode = eogEdge.getEnd();
            if (nextNode.getLocation() == null)
                continue;
            graph.addEdge(eogEdge.getStart(), eogEdge.getEnd(), "eog");
        }
    }

    private void visitDFG(Node n) {
        for (Node nextDFG: n.getNextDFG()) {
            if (nextDFG.getLocation() == null)
                continue;
            graph.addEdge(n, nextDFG, "dfg");
        }
    }

    public CustomizedGraph getGraph() {
        return graph;
    }
}
