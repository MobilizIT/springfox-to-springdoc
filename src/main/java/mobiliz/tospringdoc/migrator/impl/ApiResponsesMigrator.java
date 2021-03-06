package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.annotations.ApiResponses;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;

public class ApiResponsesMigrator extends AbstractAnnotationMigrator {

    private final ApiResponseMigrator apiResponseMigrator = new ApiResponseMigrator();

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiResponses.class, io.swagger.v3.oas.annotations.responses.ApiResponses.class);
        MemberValuePair valuePair = expr.getPairs().get(0);
        if (valuePair.getValue() instanceof NormalAnnotationExpr) {
            apiResponseMigrator.migrate((NormalAnnotationExpr) valuePair.getValue());
            return;
        }

        if (valuePair.getValue() instanceof ArrayInitializerExpr) {
            ArrayInitializerExpr responseAnnos = (ArrayInitializerExpr) valuePair.getValue();
            for (Expression respAnno : responseAnnos.getValues()) {
                apiResponseMigrator.migrate((NormalAnnotationExpr) respAnno);
            }
        }
    }

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiResponses.class, io.swagger.v3.oas.annotations.responses.ApiResponses.class);
    }
}
