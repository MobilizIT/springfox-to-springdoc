package mobiliz.tospringdoc.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jakarta.servlet.http.HttpServletResponse;

public class ResponseUtils {
    public static final String LIST = "List";
    public static final String SET = "Set";
    public static final String MAP = "Map";

    public static Integer resolveResponseCode(String codeExpr) {
        if (codeExpr == null) {
            return null;
        }
        Binding binding = new Binding();
        binding.setProperty("HttpServletResponse", HttpServletResponse.class);
        return (Integer) new GroovyShell(binding).evaluate("return " + codeExpr);
    }

    public static boolean isArraySchemaRequired(String responseContainer) {
        // TODO handle MAP
        return ResponseUtils.LIST.equals(responseContainer) || ResponseUtils.SET.equals(responseContainer);
    }
}
