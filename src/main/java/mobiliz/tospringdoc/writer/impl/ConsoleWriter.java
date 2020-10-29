package mobiliz.tospringdoc.writer.impl;

import mobiliz.tospringdoc.core.MigrationUnit;
import mobiliz.tospringdoc.writer.SourceWriter;

public class ConsoleWriter implements SourceWriter {

    private boolean titled = true;

    @Override
    public void write(MigrationUnit migrationUnit) {
        if (titled) {
            System.out.println(migrationUnit.getRelativePath() + ":");
        }
        System.out.print(migrationUnit.getCompilationUnit().toString());
    }

    public boolean isTitled() {
        return titled;
    }

    public void setTitled(boolean titled) {
        this.titled = titled;
    }
}
