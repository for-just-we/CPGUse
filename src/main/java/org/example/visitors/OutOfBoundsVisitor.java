package org.example.visitors;

import de.fraunhofer.aisec.cpg.analysis.ValueEvaluator;
import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.declarations.Declaration;
import de.fraunhofer.aisec.cpg.graph.declarations.VariableDeclaration;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.ArrayCreationExpression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.ArraySubscriptionExpression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.DeclaredReferenceExpression;
import de.fraunhofer.aisec.cpg.graph.statements.expressions.Expression;
import de.fraunhofer.aisec.cpg.processing.IVisitor;
import org.jetbrains.annotations.NotNull;


public class OutOfBoundsVisitor extends IVisitor<Node> {
    @Override
    public void visit(@NotNull Node t) {
        if (t instanceof ArraySubscriptionExpression subscriptionExpression)
            visit(subscriptionExpression);
        super.visit(t);
    }

    // 只能检查由array[index]引发的异常，并且index一定是常量，同时array由 ''Type array = new Type[len]''，这样的形式定义
    private void visit(ArraySubscriptionExpression subscriptionExpression) {
        ValueEvaluator evaluator = new ValueEvaluator();
        // getSubscriptExpression 返回索引对应的表达式
        System.out.printf("""
                        =============================================
                        Array index: %s --- index is: %s ---- array is: %s
                        """, subscriptionExpression.getCode(),
                subscriptionExpression.getSubscriptExpression().getCode(), subscriptionExpression.getArrayExpression().getCode());
        Object resolvedIndex = evaluator.evaluate(subscriptionExpression.getSubscriptExpression());
        if (resolvedIndex instanceof Number index) {
            System.out.printf("resolved index is %d\n", index.intValue());
            // check, if we know that the array was initialized with a fixed length
            // TODO(oxisto): it would be nice to have a helper that follows the expr
            if (subscriptionExpression.getArrayExpression() instanceof DeclaredReferenceExpression declRefExpr) {
                Declaration decl = declRefExpr.getRefersTo();
                System.out.printf("ArrayDecl ref: %s\n",declRefExpr.getCode());

                if (decl instanceof VariableDeclaration varDecl) {
                    System.out.printf("refers to : %s\n", varDecl.getCode());
                    Expression initializer = varDecl.getInitializer();

                    if (initializer instanceof ArrayCreationExpression arrayCreationExpr) {
                        System.out.printf("array creation: %s\n",arrayCreationExpr.getCode());
                        if (arrayCreationExpr.getDimensions().size() == 0)
                            return;
                        Object objLength = evaluator.evaluate(arrayCreationExpr.getDimensions().get(0));
                        if (objLength instanceof Number length)
                            if (index.intValue() >= length.intValue())
                                System.out.printf("find out of bounds access in %s:%s, array define in %s:%s\n" +
                                                "-------------------------------------------\n",
                                        subscriptionExpression.getCode(), subscriptionExpression.getLocation(),
                                        varDecl.getCode(), varDecl.getLocation());
                    }
                }
            }
        }
        else
            System.out.println("cound not resolve this");
    }
}
