package mobiliz.tospringdoc.writer.impl;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;


import mobiliz.tospringdoc.core.MigrationUnit;
import org.junit.Assert;
import org.junit.Test;

public class ConsoleWriterTest extends AbstractSourceWriterTest {

    @Test
    public void printsSourceToStdoutWithPath() throws Exception {
        loadSource();
        ConsoleWriter consoleWriter = new ConsoleWriter();
        String out = tapSystemOut(() -> {
            consoleWriter.write(new MigrationUnit("/tmp/TestController.java", compilationUnit));
        });
        String expected = "/tmp/TestController.java:\n" + compilationUnit.toString();
        Assert.assertEquals(expected, out);
    }

    @Test
    public void printsSourceToStdoutWithoutPath() throws Exception {
        loadSource();
        ConsoleWriter consoleWriter = new ConsoleWriter();
        consoleWriter.setTitled(false);
        String out = tapSystemOut(() -> {
            consoleWriter.write(new MigrationUnit("/tmp/TestController.java", compilationUnit));
        });
        Assert.assertEquals(compilationUnit.toString(), out);
    }
}
