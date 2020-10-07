package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import io.swagger.annotations.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractSchemaHolderAnnotationMigrator;
import mobiliz.tospringdoc.util.ResponseUtils;

public class ApiResponseMigrator extends AbstractSchemaHolderAnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiResponse.class, io.swagger.v3.oas.annotations.responses.ApiResponse.class);
        if (isProcessed(expr) || expr.getPairs() == null) {
            return;
        }
        List<MemberValuePair> pairs = new ArrayList<>(expr.getPairs());
        expr.getPairs().clear();
        String response = null;
        String responseContainer = null;
        for (MemberValuePair pair : pairs) {
            switch (pair.getNameAsString()) {
                case Attributes.CODE:
                    int responseCode = ResponseUtils.resolveResponseCode(pair.getValue().toString());
                    expr.addPair(Attributes.RESPONSE_CODE, new StringLiteralExpr(String.valueOf(responseCode)));
                    break;
                case Attributes.MESSAGE:
                    expr.addPair(Attributes.DESCRIPTION, pair.getValue());
                    break;
                case Attributes.RESPONSE:
                    response = pair.getValue().toString();
                    break;
                case Attributes.RESPONSE_CONTAINER:
                    responseContainer = pair.getValue().toString();
            }
        }
        applyResponse(expr, response, responseContainer);
    }

    private boolean isProcessed(NormalAnnotationExpr expr) {
        NodeList<MemberValuePair> pairs = expr.getPairs();
        if (pairs == null || pairs.isEmpty()) {
            return false;
        }
        for (MemberValuePair pair : pairs) {
            if (pair.getNameAsString().equals(Attributes.RESPONSE_CODE)) {
                return true;
            }
        }
        return false;
    }
}
