package mobiliz.tospringdoc;

import com.github.javaparser.ast.expr.NormalAnnotationExpr;

public interface AnnotationMigrator {
    void migrate(NormalAnnotationExpr expr);
}
