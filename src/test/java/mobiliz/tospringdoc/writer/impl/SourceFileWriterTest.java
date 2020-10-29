package mobiliz.tospringdoc.writer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import mobiliz.tospringdoc.core.MigrationUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SourceFileWriterTest extends AbstractSourceWriterTest {

    @Test
    public void writesCompilationUnitToFile() throws IOException {
        loadSource();
        String tmpDir = System.getProperty("java.io.tmpdir");
        String relativePath = "/TestController.java";
        SourceFileWriter sourceFileWriter = new SourceFileWriter(tmpDir);
        sourceFileWriter.write(new MigrationUnit(relativePath, compilationUnit));
        String actual = IOUtils.toString(new FileInputStream(tmpDir + relativePath), StandardCharsets.UTF_8);
        Assert.assertEquals(sourceCode, actual);
    }

    @Test
    public void createsTargetDirectoriesIfNotExists() throws IOException {
        loadSource();
        String relativePath = "src/mobiliz/controller/TestController.java";
        File parentFolder = new File(System.getProperty("java.io.tmpdir") + "/src");
        if (parentFolder.exists()) {
            FileUtils.deleteDirectory(parentFolder);
        }
        SourceFileWriter sourceFileWriter = new SourceFileWriter(parentFolder.getAbsolutePath());
        sourceFileWriter.write(new MigrationUnit(relativePath, compilationUnit));
        Assertions.assertTrue(new File(parentFolder.getAbsolutePath() + relativePath).exists());
    }
}
