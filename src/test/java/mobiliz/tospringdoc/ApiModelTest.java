package mobiliz.tospringdoc;

import io.swagger.annotations.ApiModel;
import java.io.IOException;
import org.junit.Test;

public class ApiModelTest extends AbstractSampleTest {

    public ApiModelTest() {
        super(ApiModel.class);
    }

    @Test
    public void marker() throws IOException {
        testBySample("marker");
    }

    @Test
    public void withName() throws IOException {
        testBySample("with_name");
    }

    @Test
    public void singleWithName() throws IOException {
        testBySample("single_with_name");
    }

    @Test
    public void withNameAndDescription() throws IOException {
        testBySample("with_name_and_description");
    }
}
