package org.example.visitors;

import de.fraunhofer.aisec.cpg.ConfigurationException;
import de.fraunhofer.aisec.cpg.TranslationResult;
import de.fraunhofer.aisec.cpg.processing.strategy.Strategy;
import org.example.schemas.CustomizedGraph;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class GraphBuilderTest extends TestUtil{
    private final String testFile = "src/test/testfiles/test1.c";

    @Test
    public void test() throws ConfigurationException, ExecutionException, InterruptedException {
        TranslationResult translationResult = getTransResult(testFile);
        GraphBuilder builder = new GraphBuilder();
        translationResult.accept(Strategy::AST_FORWARD, builder);

        CustomizedGraph graph = builder.getGraph();
        graph.getEdges().get("eog").forEach(edge -> {
            System.out.printf("evaluation order edge: %s\n", edge);
        });
        graph.getEdges().get("dfg").forEach(edge -> {
            System.out.printf("data flow edge: %s\n", edge);
        });
    }
}