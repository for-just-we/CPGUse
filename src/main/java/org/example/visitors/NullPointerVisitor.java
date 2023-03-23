package org.example.visitors;

import de.fraunhofer.aisec.cpg.analysis.ValueEvaluator;
import de.fraunhofer.aisec.cpg.graph.HasBase;
import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.declarations.Declaration;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.ArraySubscriptionExpression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.Expression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.MemberCallExpression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.MemberExpression;
import de.fraunhofer.aisec.cpg.processing.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// 空指针检测，通过遍历DataFlowGraph实现
public class NullPointerVisitor extends IVisitor<Node> {
    @Override
    public void visit(@NotNull Node t) {
        if (t instanceof MemberCallExpression || t instanceof MemberExpression
                || t instanceof ArraySubscriptionExpression)
            handleHasBase((HasBase) t);

        super.visit(t);
    }

    private void handleHasBase(HasBase n) {
        Expression pointer = n.getBase();
        ValueEvaluator evaluator = new ValueEvaluator();
        if (pointer == null)
            return;
        pointer.getPrevDFG().forEach(it -> {
            if (it instanceof Expression || it instanceof Declaration) {
                System.out.printf("============================================\nprev DFG: %s-%s\n", it.getClass().getSimpleName(), it.getCode());
                Object result = evaluator.evaluate(it);
                System.out.printf("evaluate result: %s\n", result);

                if (result == null)
                    System.out.printf("find null pointer dereference in %s:%s, array define in %s:%s\n",
                            Objects.requireNonNull(n.getBase()).getCode(), n.getBase().getLocation(),
                            it.getCode(), it.getLocation());

                else if (result instanceof Number resultNum)
                    if (resultNum.intValue() == 0)
                        System.out.printf("find null pointer dereference in %s:%s, array define in %s:%s\n" +
                                        "*********************************\n",
                                Objects.requireNonNull(n.getBase()).getCode(), n.getBase().getLocation(),
                                it.getCode(), it.getLocation());
            }
        });
    }
}
