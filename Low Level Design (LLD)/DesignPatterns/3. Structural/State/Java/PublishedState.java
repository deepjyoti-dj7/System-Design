public class PublishedState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Cannot edit a published document.");
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Document is already published.");
    }
}
