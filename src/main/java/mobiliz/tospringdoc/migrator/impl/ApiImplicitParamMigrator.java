package mobiliz.tospringdoc.migrator.impl;

import io.swagger.annotations.ApiImplicitParam;
import java.lang.annotation.Annotation;

public class ApiImplicitParamMigrator extends AbstractApiParamMigrator {
    @Override
    protected Class<? extends Annotation> getFoxAnnotation() {
        return ApiImplicitParam.class;
    }
}
