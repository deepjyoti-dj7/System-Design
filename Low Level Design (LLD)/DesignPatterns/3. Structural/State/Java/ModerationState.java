public class ModerationState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Cannot edit while in moderation.");
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Publishing document.");
        doc.setState(new PublishedState());
    }
}
