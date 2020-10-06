package mobiliz.tospringdoc;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static String sampleClass = "MobileReportController.java";


    public static void main(String... args) throws FileNotFoundException {
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> parse = javaParser.parse(new File(sampleClass));
        CompilationUnit compilationUnit = parse.getResult().get();
        ToSpringDocVisitor toSpringDoc = new ToSpringDocVisitor();
        toSpringDoc.visit(compilationUnit, null);
        System.out.println(compilationUnit);
    }
}
