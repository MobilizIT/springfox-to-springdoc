package mobiliz.tospringdoc;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiResponseMigrator implements AnnotationMigrator {

    @Override
    public void migrate(NormalAnnotationExpr expr) {
        if (isProcessed(expr) || expr.getPairs() == null) {
            return;
        }
        List<MemberValuePair> pairs = new ArrayList<>(expr.getPairs());
        expr.getPairs().clear();
        for (MemberValuePair pair : pairs) {
            switch (pair.getNameAsString()) {
                case Attributes.CODE:
                    expr.addPair(Attributes.RESPONSE_CODE, resolveResponseCode(pair.getValue().toString()));
                    break;
                case Attributes.MESSAGE:
                    expr.addPair(Attributes.DESCRIPTION, pair.getValue());
            }
        }
    }

    public boolean isProcessed(NormalAnnotationExpr expr) {
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

    public String resolveResponseCode(String code) {
        Binding binding = new Binding();
        binding.setProperty("HttpServletResponse", HttpServletResponse.class);
        return new GroovyShell(binding).evaluate("return " + code).toString();
    }
}
