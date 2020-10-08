package mobiliz.tospringdoc.migrator;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

public interface AnnotationMigrator {
    void migrate(NormalAnnotationExpr expr);

    void migrate(MarkerAnnotationExpr expr);

    void migrate(SingleMemberAnnotationExpr expr);
}
