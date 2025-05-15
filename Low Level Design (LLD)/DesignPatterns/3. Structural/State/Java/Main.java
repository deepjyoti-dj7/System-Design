public class Main {
    public static void main(String[] args) {
        Document doc = new Document();

        doc.edit();     // Draft: editable
        doc.publish();  // Move to Moderation

        doc.edit();     // Moderation: not editable
        doc.publish();  // Move to Published

        doc.edit();     // Published: not editable
        doc.publish();  // Already published
    }
}
