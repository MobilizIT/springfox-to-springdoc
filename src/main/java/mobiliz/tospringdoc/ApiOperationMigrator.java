package mobiliz.tospringdoc;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiOperationMigrator implements AnnotationMigrator<NormalAnnotationExpr> {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        expr.setName("Operation");
        List<MemberValuePair> pairs = new ArrayList<>(expr.getPairs());
        String response = null;
        String responseContainer = null;
        for (MemberValuePair pair : pairs) {
            String name = pair.getName().asString();
            switch (name) {
                case "value":
                    pair.setName(new SimpleName("summary"));
                    break;
                case "notes":
                    pair.setName(new SimpleName("description"));
                case "response":
                    response = pair.getValue().toString();
                    expr.getPairs().remove(pair);
                    break;
                case "responseContainer":
                    response = pair.getValue().toString();
                    expr.getPairs().remove(pair);
            }
        }
        if (response != null) {
            applyResponseOk(expr, response, responseContainer);
        }
    }

    private void applyResponseOk(NormalAnnotationExpr expr, String response, String responseContainer) {
        NormalAnnotationExpr responseOkExpr = getResponseOkExpr(expr);
        MemberValuePair responseCode
    }


    private NormalAnnotationExpr getResponseOkExpr(NormalAnnotationExpr expr) {
        MethodDeclaration parentNode = (MethodDeclaration) expr.getParentNode().get();
        List<NormalAnnotationExpr> exprs = parentNode.findAll(NormalAnnotationExpr.class, e -> {
            NodeList<MemberValuePair> pairs = e.getPairs();
            if (pairs == null || pairs.isEmpty()) {
                return false;
            }

            for (MemberValuePair pair : pairs) {
                String value = pair.getValue().toString();
                if ("200".equals(value) || "HttpServletResponse.SC_OK".equals(value)) {
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
