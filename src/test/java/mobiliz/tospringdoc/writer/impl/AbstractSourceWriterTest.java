package mobiliz.tospringdoc.writer.impl;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public abstract class AbstractSourceWriterTest {
    protected CompilationUnit compilationUnit;
    protected String sourceCode;

    public void loadSource() throws IOException {
        sourceCode = IOUtils.toString(this.getClass().getResourceAsStream("/samples/Api/marker.springdoc"), StandardCharsets.UTF_8);
        ParseResult<CompilationUnit> parsed = new JavaParser().parse(sourceCode);
        compilationUnit = parsed.getResult().get();
    }
}
