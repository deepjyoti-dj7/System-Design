public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("Root");
        File file1 = new File("Resume.pdf");
        File file2 = new File("Photo.png");

        Directory docs = new Directory("Documents");
        docs.add(file1);

        Directory images = new Directory("Images");
        images.add(file2);

        root.add(docs);
        root.add(images);

        Visitor visitor = new NamePrinterVisitor();
        root.accept(visitor);
    }
}
