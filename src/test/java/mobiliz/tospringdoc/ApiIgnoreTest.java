package mobiliz.tospringdoc;

import java.io.IOException;
import org.junit.Test;
import springfox.documentation.annotations.ApiIgnore;

public class ApiIgnoreTest extends AbstractSampleTest {
    public ApiIgnoreTest() {
        super(ApiIgnore.class);
    }

    @Test
    public void markerType() throws IOException {
        testBySample("marker_type");
    }

    @Test
    public void normalType() throws IOException {
        testBySample("normal_type");
    }

    @Test
    public void singleType() throws IOException {
        testBySample("single_type");
    }

    @Test
    public void markerMethod() throws IOException {
        testBySample("marker_method");
    }

    @Test
    public void normalMethod() throws IOException {
        testBySample("normal_method");
    }

    @Test
    public void markerParameterWithoutParameterAnnotation() throws IOException {
        testBySample("marker_parameter_without_parameter_annotation");
    }

    @Test
    public void normalParameterWithoutParameterAnnotation() throws IOException {
        testBySample("normal_parameter_without_parameter_annotation");
    }
}
