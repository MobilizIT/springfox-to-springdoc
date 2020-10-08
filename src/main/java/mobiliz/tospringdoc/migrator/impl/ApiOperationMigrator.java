package mobiliz.tospringdoc.migrator.impl;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import mobiliz.tospringdoc.core.Attributes;
import mobiliz.tospringdoc.migrator.AbstractAnnotationMigrator;
import mobiliz.tospringdoc.util.NodeUtils;
import mobiliz.tospringdoc.util.ResponseUtils;

public class ApiOperationMigrator extends AbstractAnnotationMigrator {

    private final ApiResponseMigrator apiResponseMigrator = new ApiResponseMigrator();

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiOperation.class, Operation.class);
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

    @Override
    public void migrate(MarkerAnnotationExpr expr) {
        replaceOrAddImport(expr, ApiOperation.class, Operation.class);
        expr.setName(Operation.class.getSimpleName());
    }

    private void applyResponseOk(NormalAnnotationExpr expr, String response, String responseContainer) {
        NormalAnnotationExpr responseOkExpr = getResponseOkExpr(expr);
        apiResponseMigrator.migrate(responseOkExpr);
        if (NodeUtils.getPair(responseOkExpr, Attributes.RESPONSE_CODE) == null) {
            responseOkExpr.addPair(Attributes.RESPONSE_CODE, new StringLiteralExpr("200"));
        }
        NodeUtils.applyResponse(responseOkExpr, response, responseContainer);
    }


    private NormalAnnotationExpr getResponseOkExpr(NormalAnnotationExpr expr) {
        Node parentNode = expr.getParentNode().get();
        List<NormalAnnotationExpr> exprs = parentNode.findAll(NormalAnnotationExpr.class);

        for (NormalAnnotationExpr e : exprs) {
            if (e.getNameAsString().equals(ApiResponse.class.getSimpleName())) {
                NodeList<MemberValuePair> pairs = e.getPairs();
                if (pairs != null && !pairs.isEmpty()) {
                    for (MemberValuePair pair : pairs) {
                        if (pair.getNameAsString().equals(Attributes.CODE)) {
                            Integer responseCode = ResponseUtils.resolveResponseCode(pair.getValue().toString());
                            if (responseCode != null && responseCode >= 200 && responseCode < 400) {
                                return e;
                            }
                        }
                    }
                }
            }
        }
        return ((NodeWithAnnotations) parentNode).addAndGetAnnotation(ApiResponse.class);
    }
}
