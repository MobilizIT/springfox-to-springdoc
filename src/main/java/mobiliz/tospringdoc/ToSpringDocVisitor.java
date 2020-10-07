package mobiliz.tospringdoc;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.Map;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiIgnoreMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiOperationMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiResponseMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiResponsesMigrator;
import springfox.documentation.annotations.ApiIgnore;

public class ToSpringDocVisitor extends ModifierVisitor<Object> {

    private static Map<String, AbstractAnnotationMigrator> ANNO_MIGRATE_MAP = new HashMap<>();

    static {
        ANNO_MIGRATE_MAP.put(Api.class.getSimpleName(), new ApiMigrator());
        ANNO_MIGRATE_MAP.put(ApiIgnore.class.getSimpleName(), new ApiIgnoreMigrator());
        ANNO_MIGRATE_MAP.put(ApiOperation.class.getSimpleName(), new ApiOperationMigrator());
        ANNO_MIGRATE_MAP.put(ApiResponse.class.getSimpleName(), new ApiResponseMigrator());
        ANNO_MIGRATE_MAP.put(ApiResponses.class.getSimpleName(), new ApiResponsesMigrator());
    }

    @Override
    public Visitable visit(NormalAnnotationExpr n, Object arg) {
        String name = n.getNameAsString();
        if (ANNO_MIGRATE_MAP.containsKey(name)) {
            ANNO_MIGRATE_MAP.get(name).migrate(n);
        }
        return n;
    }

    @Override
    public Visitable visit(MarkerAnnotationExpr n, Object arg) {
        String name = n.getNameAsString();
        if (ANNO_MIGRATE_MAP.containsKey(name)) {
            ANNO_MIGRATE_MAP.get(name).migrate(n);
        }
        return n;
    }
}
