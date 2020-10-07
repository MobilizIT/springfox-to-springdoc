package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;
import springfox.documentation.annotations.ApiIgnore;

public class ApiIgnoreMigrator extends AbstractAnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        expr.getParentNode().ifPresent(node -> {
            if (node instanceof com.github.javaparser.ast.body.Parameter) {
                replaceOrAddImport(expr, ApiIgnore.class, Parameter.class);
                expr.setName(Parameter.class.getSimpleName());
                expr.getPairs().clear();
                expr.addPair(Attributes.HIDDEN, new BooleanLiteralExpr(true));
            } else {
                migrateToHidden(expr);
            }
        });
    }

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        expr.getParentNode().ifPresent(node -> {
            if (node instanceof com.github.javaparser.ast.body.Parameter) {
                replaceOrAddImport(expr, ApiIgnore.class, Parameter.class);
                node.remove(expr);
                NormalAnnotationExpr parameterExpr = ((com.github.javaparser.ast.body.Parameter) node).addAndGetAnnotation(Parameter.class);
                parameterExpr.addPair(Attributes.HIDDEN, new BooleanLiteralExpr(true));
            } else {
                migrateToHidden(expr);
            }
        });
    }

    private void migrateToHidden(AnnotationExpr expr) {
        replaceOrAddImport(expr, ApiIgnore.class, Hidden.class);
        expr.setName(Hidden.class.getSimpleName());
    }
}
