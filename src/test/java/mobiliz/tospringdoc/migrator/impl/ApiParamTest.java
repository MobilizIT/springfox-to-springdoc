package mobiliz.tospringdoc.migrator.impl;

import io.swagger.annotations.ApiParam;
import java.io.IOException;
import org.junit.Test;

public class ApiParamTest extends AbstractSampleTest{

    public ApiParamTest() {
        super(ApiParam.class);
    }

    @Test
    public void marker() throws IOException {
        testBySample("marker");
    }

    @Test
    public void markerParameterWithoutParameter() throws IOException {
        testBySample("marker_parameter_without_parameter");
    }

    @Test
    public void normalParameterWithValue() throws IOException {
        testBySample("normal_parameter_with_value");
    }

    @Test
    public void normalParameterWithRequired() throws IOException {
        testBySample("normal_parameter_with_required");
    }

    @Test
    public void normalParameterWithName() throws IOException {
        testBySample("normal_parameter_with_name");
    }

    @Test
    public void normalParameterWithMultipleParameters() throws IOException {
        testBySample("normal_parameter_with_multiple_parameters");
    }
}
