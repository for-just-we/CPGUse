package org.example.visitors;

import de.fraunhofer.aisec.cpg.ConfigurationException;
import de.fraunhofer.aisec.cpg.TranslationResult;
import de.fraunhofer.aisec.cpg.processing.strategy.Strategy;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class OutOfBoundsVisitorTest extends TestUtil{
    private final String testFile = "src/test/testfiles/OOBTest.cpp";
    @Test
    public void test() throws ConfigurationException, ExecutionException, InterruptedException {
        TranslationResult translationResult = getTransResult(testFile);
        translationResult.accept(Strategy::AST_FORWARD, new OutOfBoundsVisitor());
    }
}