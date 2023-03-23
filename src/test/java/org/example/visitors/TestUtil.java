package org.example.visitors;

import de.fraunhofer.aisec.cpg.ConfigurationException;
import de.fraunhofer.aisec.cpg.TranslationConfiguration;
import de.fraunhofer.aisec.cpg.TranslationManager;
import de.fraunhofer.aisec.cpg.TranslationResult;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class TestUtil {
    public TranslationResult getTransResult(String sourceFile) throws ExecutionException, InterruptedException, ConfigurationException {
        File file = new File(sourceFile);
        TranslationConfiguration configuration = new TranslationConfiguration.Builder().sourceLocations(file).defaultPasses().defaultLanguages().build();
        TranslationManager translationManager = TranslationManager.builder().config(configuration).build();
        return translationManager.analyze().get();
    }
}
