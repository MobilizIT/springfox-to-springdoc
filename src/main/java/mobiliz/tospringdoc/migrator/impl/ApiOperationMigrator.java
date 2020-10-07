package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import mobiliz.tospringdoc.Attributes;
import mobiliz.tospringdoc.util.ResponseUtils;

public class ApiOperationMigrator extends AbstractSchemaHolderAnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        expr.setName(Operation.class.getSimpleName());
        String response = null;
        String responseContainer = null;
        for (MemberValuePair pair : expr.getPairs()) {
            String name = pair.getName().asString();
            switch (name) {
                case Attributes.VALUE:
                    pair.setName(new SimpleName(Attributes.SUMMARY));
                    break;
                case Attributes.NOTES:
                    pair.setName(new SimpleName(Attributes.DESCRIPTION));
                case Attributes.RESPONSE:
                    response = pair.getValue().toString();
                    break;
                case Attributes.RESPONSE_CONTAINER:
                    response = pair.getValue().toString();
            }
        }
        if (response != null) {
            applyResponseOk(expr, response, responseContainer);
        }
        expr.getPairs().removeIf(pair -> Attributes.RESPONSE.equals(pair.getNameAsString()) || Attributes.RESPONSE_CONTAINER.equals(pair.getNameAsString()));
    }

    private void applyResponseOk(NormalAnnotationExpr expr, String response, String responseContainer) {
        NormalAnnotationExpr responseOkExpr = getResponseOkExpr(expr);
        responseOkExpr.getPairs().clear();
        responseOkExpr.addPair(Attributes.RESPONSE_CODE, new StringLiteralExpr("200"));
        applyResponse(responseOkExpr, response, responseContainer);
    }


    private NormalAnnotationExpr getResponseOkExpr(NormalAnnotationExpr expr) {
        MethodDeclaration parentNode = (MethodDeclaration) expr.getParentNode().get();
        List<NormalAnnotationExpr> exprs = parentNode.findAll(NormalAnnotationExpr.class, e -> {
            if (!e.getNameAsString().equals(ApiResponse.class.getSimpleName())) {
                return false;
            }
            NodeList<MemberValuePair> pairs = e.getPairs();
            if (pairs == null || pairs.isEmpty()) {
                return false;
            }

            for (MemberValuePair pair : pairs) {
                if (pair.getNameAsString().equals(Attributes.RESPONSE_CODE)) {
                    Integer responseCode = ResponseUtils.resolveResponseCode(pair.getValue().toString());
                    if (responseCode != null && responseCode >= 200 && responseCode < 400) {
                        return true;
                    }
                }
            }
            return false;
        });
        if (!exprs.isEmpty()) {
            return exprs.get(0);
        }
        return parentNode.addAndGetAnnotation(ApiResponse.class);
    }
}
