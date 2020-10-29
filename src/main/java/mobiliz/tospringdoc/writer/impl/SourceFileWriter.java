package mobiliz.tospringdoc.writer.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import mobiliz.tospringdoc.core.MigrationUnit;
import mobiliz.tospringdoc.writer.SourceWriter;


public class SourceFileWriter implements SourceWriter {

    private String targetPath;

    public SourceFileWriter(String targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public void write(MigrationUnit migrationUnit) throws IOException {
        File file = new File(targetPath + migrationUnit.getRelativePath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(migrationUnit.getCompilationUnit().toString());
        fileWriter.close();
    }
}
