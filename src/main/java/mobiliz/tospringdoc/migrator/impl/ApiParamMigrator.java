package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;
import mobiliz.tospringdoc.util.NodeUtils;

public class ApiParamMigrator extends AbstractAnnotationMigrator{

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        MemberValuePair valuePair = NodeUtils.getPair(expr, Attributes.VALUE);
        if(valuePair != null){
            valuePair.setName(Attributes.DESCRIPTION);
        }
        migrateImportAndAnnotations(expr);
    }

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        migrateImportAndAnnotations(expr);
    }

    private void migrateImportAndAnnotations(AnnotationExpr expr) {
        replaceOrAddImport(expr, ApiParam.class, Parameter.class);
        expr.setName(Parameter.class.getSimpleName());
    }

}
