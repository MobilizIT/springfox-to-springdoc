package mobiliz.tospringdoc.util;

import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

public final class NodeUtils {

    public static MemberValuePair getPair(NormalAnnotationExpr expr, String pairName) {
        if (expr.getPairs() == null) {
            return null;
        }

        for (MemberValuePair pair : expr.getPairs()) {
            if (pair.getNameAsString().equals(pairName)) {
                return pair;
            }
        }
        return null;
    }
}
