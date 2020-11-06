package mobiliz.tospringdoc.migrator.impl;

import io.swagger.annotations.ApiImplicitParam;
import java.io.IOException;
import org.junit.Test;

public class ApiImplicitParamTest extends AbstractSampleTest {

    public ApiImplicitParamTest() {
        super(ApiImplicitParam.class);
    }

    @Test
    public void marker() throws IOException {
        testBySample("marker");
    }

    @Test
    public void normalWithUnchangedProperties() throws IOException {
        testBySample("normal_with_unchanged_properties");
    }

    @Test
    public void normalWithValue() throws IOException {
        testBySample("normal_with_value");
    }

    @Test
    public void normalWithparamType() throws IOException {
        testBySample("normal_with_paramType");
    }
}
