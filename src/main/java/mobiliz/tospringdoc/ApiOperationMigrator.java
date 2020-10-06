package mobiliz.tospringdoc;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

public class ApiOperationMigrator implements AnnotationMigrator {

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
        MethodDeclaration parentNode = (MethodDeclaration) expr.getParentNode().get();
        NormalAnnotationExpr responseOkExpr = getResponseOkExpr(expr);
        responseOkExpr.getPairs().clear();
        // TODO handle ResponseContainerType.MAP
        NormalAnnotationExpr content = null;
        if (ResponseContainerType.LIST.equals(responseContainer) || ResponseContainerType.SET.equals(responseContainer)) {
            content = NodeFactory.createArrayContentExpr(response);
            parentNode.tryAddImportToParentCompilationUnit(ArraySchema.class);
        } else {
            content = NodeFactory.createContentExpr(response);
        }
        responseOkExpr.addPair(Attributes.RESPONSE_CODE, "200");
        responseOkExpr.addPair(Attributes.CONTENT, content);
        parentNode.tryAddImportToParentCompilationUnit(Schema.class);
        parentNode.tryAddImportToParentCompilationUnit(Content.class);
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
                String value = pair.getValue().toString();
                if ("\"200\"".equals(value) || "200".equals(value) || "HttpServletResponse.SC_OK".equals(value)) {
                    return true;
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
