package mobiliz.tospringdoc.core;

import com.github.javaparser.ast.CompilationUnit;

public class MigrationUnit {
    private String relativePath;
    private CompilationUnit compilationUnit;

    public MigrationUnit(String relativePath, CompilationUnit compilationUnit) {
        this.relativePath = relativePath;
        this.compilationUnit = compilationUnit;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
}