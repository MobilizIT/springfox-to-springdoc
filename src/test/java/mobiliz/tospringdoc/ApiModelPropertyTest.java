package mobiliz.tospringdoc;

import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import org.junit.Test;

public class ApiModelPropertyTest extends AbstractSampleTest {
    public ApiModelPropertyTest() {
        super(ApiModelProperty.class);
    }

    @Test
    public void marker() throws IOException {
        testBySample("marker");
    }

    @Test
    public void single() throws IOException {
        testBySample("single");
    }

}
