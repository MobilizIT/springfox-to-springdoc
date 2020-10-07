package mobiliz.tospringdoc;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

public abstract class AbstractSampleTest {
    public static final String SAMPLE_PACKAGE = "samples";

    private Class<? extends Annotation> annotationClass;

    public AbstractSampleTest(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    protected void testBySample(String sampleName) throws IOException {
        String foxPath = String.format("/%s/%s/%s.springfox", SAMPLE_PACKAGE, annotationClass.getSimpleName(), sampleName);
        String docPath = String.format("/%s/%s/%s.springdoc", SAMPLE_PACKAGE, annotationClass.getSimpleName(), sampleName);
        ParseResult<CompilationUnit> parse = new JavaParser().parse(this.getClass().getResourceAsStream(foxPath));
        CompilationUnit compilationUnit = parse.getResult().get();
        ToSpringDocVisitor toSpringDoc = new ToSpringDocVisitor();
        toSpringDoc.visit(compilationUnit, null);
        String migratedSource = compilationUnit.toString();
        String expectedSource = IOUtils.toString(this.getClass().getResourceAsStream(docPath), StandardCharsets.UTF_8);
        Assert.assertEquals(expectedSource, migratedSource);
    }
}
