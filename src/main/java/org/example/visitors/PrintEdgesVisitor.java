package org.example.visitors;

import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.edge.PropertyEdge;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.ArrayCreationExpression;
import de.fraunhofer.aisec.cpg.processing.IVisitor;
import org.jetbrains.annotations.NotNull;

// 用来打印一个file中存在的所有EOG边和DFG边
public class PrintEdgesVisitor extends IVisitor<Node> {
    @Override
    public void visit(@NotNull Node t) {
        System.out.printf("parsing for Node: %s:%s\n", t.getClass().getSimpleName(), t.getCode());
        visitEOG(t);
        visitDFG(t);
        System.out.println("==============================");
        super.visit(t);
    }

    private void visitEOG(Node n) {
        for (PropertyEdge<Node> eogEdge: n.getNextEOGEdges()) {
            System.out.printf("evaluation order: %s:%s --------> %s:%s\n", eogEdge.getStart().getClass().getSimpleName(),
                    eogEdge.getStart().getCode(), eogEdge.getEnd().getClass().getSimpleName(), eogEdge.getEnd().getCode());
        }
    }

    private void visitDFG(Node n) {
        for (Node prevDFG: n.getPrevDFG()) {
            System.out.printf("data flow:  %s:%s --------> %s:%s\n", prevDFG.getClass().getSimpleName(),
                    prevDFG.getCode(), n.getClass().getSimpleName(), n.getCode());
        }
    }
}
