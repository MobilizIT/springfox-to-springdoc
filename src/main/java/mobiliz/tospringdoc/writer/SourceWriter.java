package mobiliz.tospringdoc.writer;

import java.io.IOException;
import mobiliz.tospringdoc.core.MigrationUnit;

public interface SourceWriter {

    void write(MigrationUnit migrationUnit) throws IOException;
}
