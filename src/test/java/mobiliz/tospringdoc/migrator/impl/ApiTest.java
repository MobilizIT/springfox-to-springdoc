package mobiliz.tospringdoc.migrator.impl;

import io.swagger.annotations.Api;
import java.io.IOException;
import org.junit.Test;

public class ApiTest extends AbstractSampleTest {

    public ApiTest() {
        super(Api.class);
    }

    @Test
    public void marker() throws IOException {
        testBySample("marker");
    }

    @Test
    public void normalValue() throws IOException {
        testBySample("normal_value");
    }


    @Test
    public void tagsSingle() throws IOException {
        testBySample("tags_single");
    }

    @Test
    public void multipleTagsWithoutValue() throws IOException {
        testBySample("multiple_tags_without_value");
    }

}
