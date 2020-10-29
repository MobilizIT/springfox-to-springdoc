package mobiliz.tospringdoc;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;


import org.junit.Assert;
import org.junit.Test;

public class MainTest {

    @Test
    public void printsHelp() throws Exception {
        String out = tapSystemOut(() -> {
            Main.main("-h");
        });
        String expected = "usage: java -jar stringfox-to-springdoc.jar <src> [options]\n" +
            " -h,--help        display this help and exit\n" +
            " -i,--in-place    migrate files in place\n" +
            " -o,--out <arg>   write migrated source instead of stdout. This option is\n" +
            "                  discarded if in-place option set.\n";
        Assert.assertEquals(expected, out);
    }

    @Test
    public void printErrIfSourceNotSpecified() throws Exception {
        String err = tapSystemErr(() -> {
            Main.main();
        });
        Assert.assertEquals("ERROR: source is not specified.\n", err);
    }
}
