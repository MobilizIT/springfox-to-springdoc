package mobiliz.tospringdoc.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class FileUtilsTest {

    @Test
    public void getsJavaFiles() throws IOException {
        List<File> javaFiles = FileUtils.getJavaFiles("./src/main/java/mobiliz/tospringdoc/util");
        assertNotNull(javaFiles);
        assertEquals(javaFiles.size(), 3);
        Set<String> names = javaFiles.stream().map(File::getName).collect(Collectors.toSet());
        assertTrue(names.contains("FileUtils.java"));
        assertTrue(names.contains("NodeUtils.java"));
        assertTrue(names.contains("ResponseUtils.java"));
    }
}
