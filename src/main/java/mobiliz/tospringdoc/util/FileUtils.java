package mobiliz.tospringdoc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public final class FileUtils {
    private FileUtils() {
    }


    public static List<File> getJavaFiles(String path) throws IOException {
        return Files.walk(Paths.get(path))
            .filter(Files::isRegularFile)
            .filter(p->p.toString().endsWith(".java"))
            .map(Path::toFile)
            .collect(Collectors.toList());
    }
}
