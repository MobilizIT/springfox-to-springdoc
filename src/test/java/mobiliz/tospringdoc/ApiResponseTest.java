package mobiliz.tospringdoc;

import io.swagger.annotations.ApiResponse;
import java.io.IOException;
import org.junit.Test;

public class ApiResponseTest extends AbstractSampleTest {

    public ApiResponseTest() {
        super(ApiResponse.class);
    }

    @Test
    public void codeAndMessage() throws IOException {
        testBySample("code_and_message");
    }

    @Test
    public void withResponseList() throws IOException {
        testBySample("with_response_list");
    }

}
