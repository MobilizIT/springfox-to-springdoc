package mobiliz.tospringdoc.migrator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import java.lang.annotation.Annotation;

public abstract class AbstractAnnotationMigrator implements AnnotationMigrator {

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
