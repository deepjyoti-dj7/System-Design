public interface Visitor {
    void visit(File file);
    void visit(Directory directory);
}
