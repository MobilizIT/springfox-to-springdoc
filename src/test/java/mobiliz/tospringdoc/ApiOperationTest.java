package mobiliz.tospringdoc;

import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.junit.Test;

public class ApiOperationTest extends AbstractSampleTest {
    public ApiOperationTest() {
        super(ApiOperation.class);
    }

    @Test
    public void normalValue() throws IOException {
        testBySample("normal_value");
    }

    @Test
    public void notes() throws IOException {
        testBySample("notes");
    }

    @Test
    public void withResponseWithoutApiResponse() throws IOException {
        testBySample("with_response_without_apiresponse");
    }

    @Test
    public void withResponseListWithoutApiResponse() throws IOException {
        testBySample("with_response_list_without_apiresponse");
    }

    @Test
    public void withResponseSetWithoutApiResponse() throws IOException {
        testBySample("with_response_set_without_apiresponse");
    }

    @Test
    public void withResponseSetWithScOkApiResponse() throws IOException {
        testBySample("with_response_with_SC_OK_apiresponse");
    }

    @Test
    public void withResponseSetWithScAcceptedApiResponse() throws IOException {
        testBySample("with_response_with_SC_ACCEPTED_apiresponse");
    }

}
