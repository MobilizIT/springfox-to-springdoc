package mobiliz.tospringdoc.core;

import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

public final class NodeFactory {

    private NodeFactory() {
    }

    public static ClassExpr createClassExpr(String name) {
        String identifier = name.endsWith(".class") ? name.substring(0, name.length() - 6) : name;
        ClassOrInterfaceType classOrInterfaceType = new ClassOrInterfaceType();
        classOrInterfaceType.setName(new SimpleName(identifier));
        ClassExpr expr = new ClassExpr();
        expr.setType(classOrInterfaceType);
        return expr;
    }

    public static NormalAnnotationExpr createSchemaExpr(String implementation) {
        NormalAnnotationExpr expr = new NormalAnnotationExpr();
        expr.setName(Schema.class.getSimpleName());
        expr.addPair(Attributes.IMPLEMENTATION, createClassExpr(implementation));
        return expr;
    }

    public static NormalAnnotationExpr createArraySchemaExpr(String implementation) {
        NormalAnnotationExpr expr = new NormalAnnotationExpr();
        expr.setName(ArraySchema.class.getSimpleName());
        expr.addPair(Attributes.SCHEMA, createSchemaExpr(implementation));
        return expr;
    }


    public static NormalAnnotationExpr createContentExpr(String implementation) {
        NormalAnnotationExpr expr = new NormalAnnotationExpr();
        expr.setName(Content.class.getSimpleName());
        expr.addPair(Attributes.SCHEMA, createSchemaExpr(implementation));
        return expr;
    }

    public static NormalAnnotationExpr createArrayContentExpr(String implementation) {
        NormalAnnotationExpr expr = new NormalAnnotationExpr();
        expr.setName(Content.class.getSimpleName());
        expr.addPair(Attributes.ARRAY, createArraySchemaExpr(implementation));
        return expr;
    }


}
