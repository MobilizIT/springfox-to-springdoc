package mobiliz.tospringdoc;

public interface AnnotationMigrator<T> {
    void migrate(T expr);
}
