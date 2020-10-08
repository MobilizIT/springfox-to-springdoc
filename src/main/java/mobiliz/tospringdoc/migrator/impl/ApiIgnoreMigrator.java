package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;
import mobiliz.tospringdoc.util.NodeUtils;
import springfox.documentation.annotations.ApiIgnore;

public class ApiIgnoreMigrator extends AbstractAnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        MemberValuePair valuePair = NodeUtils.getPair(expr, Attributes.VALUE);
        String value = valuePair != null ? valuePair.getValue().asStringLiteralExpr().getValue() : null;
        migrate(expr, value);
    }

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        migrate(expr, null);
    }

    @Override
    public void migrate(SingleMemberAnnotationExpr expr) {
        String value = expr.getMemberValue().asStringLiteralExpr().getValue();
        migrate(expr, value);
    }

    private void migrate(AnnotationExpr expr, String value) {
        expr.getParentNode().ifPresent(node -> {
            AnnotationExpr migrated;
            if (node instanceof com.github.javaparser.ast.body.Parameter) {
                replaceOrAddImport(expr, ApiIgnore.class, Parameter.class);
                migrated = new NormalAnnotationExpr();
                migrated.setName(Parameter.class.getSimpleName());
                ((NormalAnnotationExpr) migrated).addPair(Attributes.HIDDEN, new BooleanLiteralExpr(true));
            } else {
                replaceOrAddImport(expr, ApiIgnore.class, Hidden.class);
                migrated = new MarkerAnnotationExpr(Hidden.class.getSimpleName());
            }
            node.remove(expr);
            ((NodeWithAnnotations) node).addAnnotation(migrated);
            if (value != null && !value.isEmpty()) {
                migrated.setLineComment(value);
            }
        });
    }
}
