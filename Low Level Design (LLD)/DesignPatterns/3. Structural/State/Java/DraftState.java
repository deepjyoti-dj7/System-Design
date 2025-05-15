public class DraftState implements State {
    @Override
    public void edit(Document doc) {
        System.out.println("Editing document in draft.");
    }

    @Override
    public void publish(Document doc) {
        System.out.println("Submitting document for review.");
        doc.setState(new ModerationState());
    }
}
