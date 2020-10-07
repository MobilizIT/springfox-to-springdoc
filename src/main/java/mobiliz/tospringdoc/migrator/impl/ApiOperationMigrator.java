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
                    break;
                case Attributes.RESPONSE:
                    response = pair.getValue().toString();
                    break;
                case Attributes.RESPONSE_CONTAINER:
                    responseContainer = pair.getValue().asStringLiteralExpr().getValue();
            }
        }
        if (response != null) {
            applyResponseOk(expr, response, responseContainer);
        }
        expr.getPairs().removeIf(pair -> Attributes.RESPONSE.equals(pair.getNameAsString()) || Attributes.RESPONSE_CONTAINER.equals(pair.getNameAsString()));
    }

    private void applyResponseOk(NormalAnnotationExpr expr, String response, String responseContainer) {
        ResponseOk responseOk = getResponseOkExpr(expr);
        NormalAnnotationExpr responseOkExpr = responseOk.getExpr();
        if (responseOkExpr.getPairs() != null) {
            responseOkExpr.getPairs().clear();
        }
        responseOkExpr.addPair(Attributes.RESPONSE_CODE, new StringLiteralExpr(String.valueOf(responseOk.getResponseCode())));
        applyResponse(responseOkExpr, response, responseContainer);
    }


    private ResponseOk getResponseOkExpr(NormalAnnotationExpr expr) {
        MethodDeclaration parentNode = (MethodDeclaration) expr.getParentNode().get();
        List<NormalAnnotationExpr> exprs = parentNode.findAll(NormalAnnotationExpr.class);

        for (NormalAnnotationExpr e : exprs) {
            if (e.getNameAsString().equals(ApiResponse.class.getSimpleName())) {
                NodeList<MemberValuePair> pairs = e.getPairs();
                if (pairs != null && !pairs.isEmpty()) {
                    for (MemberValuePair pair : pairs) {
                        if (pair.getNameAsString().equals(Attributes.CODE)) {
                            Integer responseCode = ResponseUtils.resolveResponseCode(pair.getValue().toString());
                            if (responseCode != null && responseCode >= 200 && responseCode < 400) {
                                return new ResponseOk(e, responseCode);
                            }
                        }
                    }
                }
            }
        }
        return new ResponseOk(parentNode.addAndGetAnnotation(ApiResponse.class));
    }

    class ResponseOk {
        private NormalAnnotationExpr expr;
        private int responseCode;

        public ResponseOk(NormalAnnotationExpr expr) {
            this.expr = expr;
            this.responseCode = 200;
        }

        public ResponseOk(NormalAnnotationExpr expr, int responseCode) {
            this.expr = expr;
            this.responseCode = responseCode;
        }

        public NormalAnnotationExpr getExpr() {
            return expr;
        }

        public void setExpr(NormalAnnotationExpr expr) {
            this.expr = expr;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }
    }
}
