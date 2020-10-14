package mobiliz.tospringdoc.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final Options CLI_OPTIONS = new Options();

    static {
        CLI_OPTIONS.addOption("h", "help", false, "display this help and exit");
        CLI_OPTIONS.addOption("i", "in-place", false, "migrate files in place");
        CLI_OPTIONS.addOption("o", "out", false,
            "write migrated source instead of stdout. This option is discarded if in-place option set.");
    }

    public static void main(String... args) {
        CommandLine cmd = parseArgs(args);
        if (cmd.hasOption("h")) {
            pringHelp();
            return;
        }
    }

    private static void pringHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar stringfox-to-springdoc.jar <src> [options]", CLI_OPTIONS);
    }

    private static CommandLine parseArgs(String... args) {
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(CLI_OPTIONS, args);
        } catch (ParseException e) {
            pringHelp();
            System.exit(1);
            return null;
        }
    }
}
