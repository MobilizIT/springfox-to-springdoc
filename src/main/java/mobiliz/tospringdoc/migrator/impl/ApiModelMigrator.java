package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;

public class ApiModelMigrator extends AbstractAnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiModel.class, Schema.class);
        expr.setName(Schema.class.getSimpleName());
        for (MemberValuePair pair : expr.getPairs()) {
            String name = pair.getNameAsString();
            if (Attributes.VALUE.equals(name)) {
                pair.setName(Attributes.NAME);
            }
        }
    }

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiModel.class, Schema.class);
        expr.setName(Schema.class.getSimpleName());
    }
}
