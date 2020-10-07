package mobiliz.tospringdoc.migrator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import java.lang.annotation.Annotation;

public abstract class AnnotationMigrator {
    public abstract void migrate(NormalAnnotationExpr expr);

    public void replaceOrAddImport(Node node, Class<? extends Annotation> foxAnno, Class<? extends Annotation> docAnno) {
        if (node == null) {
            return;
        }
        node.findAncestor(CompilationUnit.class).ifPresent(p -> {
            if (p.getImports() != null) {
                p.getImports().removeIf(i -> i.getNameAsString().equals(foxAnno.getCanonicalName()));
            }
            p.addImport(docAnno);
        });
    }
}
