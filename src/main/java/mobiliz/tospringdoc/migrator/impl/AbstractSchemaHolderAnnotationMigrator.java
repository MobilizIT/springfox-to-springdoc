package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import mobiliz.tospringdoc.Attributes;
import mobiliz.tospringdoc.NodeFactory;
import mobiliz.tospringdoc.migrator.AnnotationMigrator;
import mobiliz.tospringdoc.util.ResponseUtils;

public abstract class AbstractSchemaHolderAnnotationMigrator implements AnnotationMigrator {

    protected void applyResponse(NormalAnnotationExpr expr, String response, String responseContainer) {
        if (expr == null || response == null) {
            return;
        }
        NormalAnnotationExpr content = null;
        if (ResponseUtils.isArraySchemaRequired(responseContainer)) {
            content = NodeFactory.createArrayContentExpr(response);
            expr.tryAddImportToParentCompilationUnit(ArraySchema.class);
        } else {
            content = NodeFactory.createContentExpr(response);
        }
        expr.addPair(Attributes.CONTENT, content);
        expr.tryAddImportToParentCompilationUnit(Schema.class);
        expr.tryAddImportToParentCompilationUnit(Content.class);
    }
}
