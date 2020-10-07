package mobiliz.tospringdoc;

import io.swagger.annotations.Api;
import java.io.IOException;
import org.junit.Test;

public class ApiTest extends AbstractSampleTest {

    public ApiTest() {
        super(Api.class);
    }

    @Test
    public void normalValue() throws IOException {
        compareSamples("normal_value");
    }

    @Test
    public void tagsSingle() throws IOException {
        compareSamples("tags_single");
    }

    @Test
    public void multipleTagsWithoutValue() throws IOException {
        compareSamples("multiple_tags_without_value");
    }

}
