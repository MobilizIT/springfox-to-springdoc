package mobiliz.tospringdoc;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.Map;
import mobiliz.tospringdoc.migrator.AnnotationMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiOperationMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiResponseMigrator;
import mobiliz.tospringdoc.migrator.impl.ApiResponsesMigrator;

public class ToSpringDocVisitor extends ModifierVisitor<Object> {

    private static Map<String, String> IMPORTS_MAP = new HashMap<>();
    private static Map<String, AnnotationMigrator> ANNO_MIGRATE_MAP = new HashMap<>();

    static {
        IMPORTS_MAP.put("io.swagger.annotations.Api", "io.swagger.v3.oas.annotations.tags.Tag");
        IMPORTS_MAP.put("io.swagger.annotations.ApiOperation", "io.swagger.v3.oas.annotations.Operation");
        IMPORTS_MAP.put("io.swagger.annotations.ApiResponse", "io.swagger.v3.oas.annotations.responses.ApiResponse");
        IMPORTS_MAP.put("io.swagger.annotations.ApiResponses", "io.swagger.v3.oas.annotations.responses.ApiResponses");
        IMPORTS_MAP.put("io.swagger.annotations.ApiParam", "io.swagger.v3.oas.annotations.Parameter");
        IMPORTS_MAP.put("io.swagger.annotations.ApiImplicitParam", "io.swagger.v3.oas.annotations.Parameter");
        IMPORTS_MAP.put("io.swagger.annotations.ApiIgnore", "io.swagger.v3.oas.annotations.Parameter");
        IMPORTS_MAP.put("io.swagger.annotations.ApiImplicitParams", "io.swagger.v3.oas.annotations.Parameters");
        IMPORTS_MAP.put("io.swagger.annotations.ApiModel", "io.swagger.v3.oas.annotations.media.Schema");
        IMPORTS_MAP.put("io.swagger.annotations.ApiModelProperty", "io.swagger.v3.oas.annotations.media.Schema");

        ANNO_MIGRATE_MAP.put(ApiOperation.class.getSimpleName(), new ApiOperationMigrator());
        ANNO_MIGRATE_MAP.put(ApiResponse.class.getSimpleName(), new ApiResponseMigrator());
        ANNO_MIGRATE_MAP.put(ApiResponses.class.getSimpleName(), new ApiResponsesMigrator());
        ANNO_MIGRATE_MAP.put(Api.class.getSimpleName(), new ApiMigrator());
    }

    @Override
    public Node visit(ImportDeclaration n, Object arg) {
        String replaceWith = IMPORTS_MAP.get(n.getNameAsString());
        if (replaceWith != null) {
            n.setName(replaceWith);
        }
        return n;
    }


    @Override
    public Visitable visit(NormalAnnotationExpr n, Object arg) {
        String name = n.getNameAsString();
        if (ANNO_MIGRATE_MAP.containsKey(name)) {
            ANNO_MIGRATE_MAP.get(name).migrate(n);
        }
        return n;
    }
}
