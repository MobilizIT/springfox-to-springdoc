package mobiliz.tospringdoc;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import mobiliz.tospringdoc.core.MigrationUnit;
import mobiliz.tospringdoc.migrator.ToSpringDocVisitor;
import mobiliz.tospringdoc.util.FileUtils;
import mobiliz.tospringdoc.writer.SourceWriter;
import mobiliz.tospringdoc.writer.impl.ConsoleWriter;
import mobiliz.tospringdoc.writer.impl.SourceFileWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final Options CLI_OPTIONS = new Options();
    private static final JavaParser JAVA_PARSER = new JavaParser();
    private static final String HELP = "h";
    private static final String IN_PLACE = "i";
    private static final String OUT = "o";

    static {
        CLI_OPTIONS.addOption(HELP, "help", false, "display this help and exit");
        CLI_OPTIONS.addOption(IN_PLACE, "in-place", false, "migrate files in place");
        CLI_OPTIONS.addOption(OUT, "out", true,
            "write migrated source instead of stdout. This option is discarded if in-place option set.");
    }

    public static void main(String... args) throws IOException {
        CommandLine cmd = parseArgs(args);
        Main main = new Main();
        main.run(cmd);
    }

    private void run(CommandLine cmd) throws IOException {
        if (cmd.hasOption(HELP)) {
            printHelp();
            return;
        }
        if (cmd.getArgList().isEmpty()) {
            System.err.println("ERROR: source is not specified.");
            return;
        }

        String sourcePath = cmd.getArgList().get(0);
        List<MigrationUnit> migrationUnits = parseSourceFiles(sourcePath);
        ToSpringDocVisitor toSpringDocVisitor = new ToSpringDocVisitor();
        migrationUnits.forEach(mu -> {
            toSpringDocVisitor.visit(mu.getCompilationUnit(), null);
        });

        boolean inPlace = cmd.hasOption(IN_PLACE);
        String outPath = inPlace ? sourcePath : cmd.getOptionValue(OUT);
        SourceWriter sourceWriter = outPath == null ? new ConsoleWriter() : new SourceFileWriter(outPath);
        migrationUnits.forEach(mu -> {
            try {
                sourceWriter.write(mu);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    private List<MigrationUnit> parseSourceFiles(String sourcePath) throws IOException {
        String absSourcePath = new File(sourcePath).getAbsolutePath();
        List<File> sourceFiles = FileUtils.getJavaFiles(sourcePath);
        return sourceFiles.stream()
            .map(sourceFile -> {
                try {
                    ParseResult<CompilationUnit> parseResult = JAVA_PARSER.parse(sourceFile);
                    String relativePath = sourceFile.getAbsolutePath().substring(absSourcePath.length());
                    if (!parseResult.isSuccessful()) {
                        printProblems(relativePath, parseResult.getProblems());
                        System.exit(1);
                    }
                    return new MigrationUnit(relativePath, parseResult.getResult().get());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                return null;
            })
            .collect(Collectors.toList());
    }


    private void printProblems(String relativePath, List<Problem> problems) {
        System.err.println(relativePath + ":");
        System.err.println(problems.stream().map(Problem::getVerboseMessage).collect(Collectors.joining("\t\n")));
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar stringfox-to-springdoc.jar <src> [options]", CLI_OPTIONS);
    }

    private static CommandLine parseArgs(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(CLI_OPTIONS, args);
        } catch (ParseException e) {
            printHelp();
            System.exit(1);
            return null;
        }
    }
}
