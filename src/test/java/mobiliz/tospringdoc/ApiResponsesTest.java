package mobiliz.tospringdoc;

import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import org.junit.Test;

public class ApiResponsesTest extends AbstractSampleTest {
    public ApiResponsesTest() {
        super(ApiResponses.class);
    }

    @Test
    public void singleApiResponse() throws IOException {
        testBySample("single_apiresponse");
    }

    @Test
    public void withApiOperationResponse() throws IOException {
        testBySample("with_apioperation_response");
    }

    @Test
    public void withoutApiOperationResponse() throws IOException {
        testBySample("without_apioperation_response");
    }
}
