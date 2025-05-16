public class NamePrinterVisitor implements Visitor {
    @Override
    public void visit(File file) {
        System.out.println("File: " + file.getName());
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("Directory: " + directory.getName());
    }
}
