public class Main {
    public static void main(String[] args) {
        FileComponent file1 = new File("Resume.pdf");
        FileComponent file2 = new File("Photo.jpg");
        FileComponent file3 = new File("Report.docx");

        Directory docs = new Directory("Documents");
        docs.add(file1);
        docs.add(file3);

        Directory images = new Directory("Images");
        images.add(file2);

        Directory root = new Directory("Home");
        root.add(docs);
        root.add(images);

        root.showDetails("");
    }
}
