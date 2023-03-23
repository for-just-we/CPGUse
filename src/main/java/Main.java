import de.fraunhofer.aisec.cpg.*;
import de.fraunhofer.aisec.cpg.frontends.TranslationException;
import de.fraunhofer.aisec.cpg.processing.strategy.Strategy;
import org.apache.commons.cli.*;

import org.example.schemas.CustomizedGraph;
import org.example.visitors.GraphBuilder;
import org.example.visitors.NullPointerVisitor;
import org.example.visitors.OutOfBoundsVisitor;
import org.example.visitors.PrintEdgesVisitor;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws ConfigurationException, ParseException, ExecutionException, InterruptedException, TranslationException {
        Options options = new Options();
        options.addOption("h", "help", false, "print options' information");
        options.addOption("f", "file", true, "path of c source file to be analyzed");

        // 创建命令行解析器
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        if(commandLine.hasOption("h")) {
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp("Options", options);
        }

        if (!options.hasOption("f")) {
            System.out.println("please specify a file to analyze");
            exit(-1);
        }

        String sourceFile = commandLine.getOptionValue("f");
        File file = new File(sourceFile);
        TranslationConfiguration configuration = new TranslationConfiguration.Builder().sourceLocations(file).defaultPasses().defaultLanguages().build();
        TranslationManager translationManager = TranslationManager.builder().config(configuration).build();
        TranslationResult translationResult = translationManager.analyze().get();

        // 打印所有的evaluation order边和data flow边
        System.out.println("Printing edges");
        translationResult.accept(Strategy::AST_FORWARD, new PrintEdgesVisitor());
        // 检测是否有缓冲区溢出
        System.out.println("Detecting array overflow read/write");
        translationResult.accept(Strategy::AST_FORWARD, new OutOfBoundsVisitor());
        // 检测是否有空指针引用
        System.out.println("Detecting null pointer reference");
        translationResult.accept(Strategy::AST_FORWARD, new NullPointerVisitor());
        // 解析为自定义图结构
        System.out.println("Parsing into customized graph structure");
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
