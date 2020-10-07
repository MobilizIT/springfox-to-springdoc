package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import mobiliz.tospringdoc.migrator.AnnotationMigrator;

public class ApiResponsesMigrator implements AnnotationMigrator {

    private final ApiResponseMigrator apiResponseMigrator = new ApiResponseMigrator();

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        if (expr.getPairs() == null || expr.getPairs().isEmpty()) {
            return;
        }

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
}
