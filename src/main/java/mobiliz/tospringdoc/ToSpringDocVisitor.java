package mobiliz.tospringdoc;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.HashMap;
import java.util.Map;

public class ToSpringDocVisitor extends VoidVisitorAdapter<Object> {

    private static Map<String, String> IMPORTS_MAP = new HashMap<>();

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
    }

    @Override
    public void visit(ImportDeclaration n, Object arg) {
        String replaceWith = IMPORTS_MAP.get(n.getNameAsString());
        if (replaceWith != null) {
            n.setName(replaceWith);
        }
    }

    @Override
    public void visit(NormalAnnotationExpr n, Object arg) {
        String name = n.getNameAsString();

        switch (name) {
            case "Hede":
                break;
            case "ApiResponses":
                break;
            case "ApiOperation":
                new ApiOperationMigrator().migrate(n);
        }
    }
}
