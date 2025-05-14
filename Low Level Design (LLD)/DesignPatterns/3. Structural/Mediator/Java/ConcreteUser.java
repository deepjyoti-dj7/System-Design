public class ConcreteUser extends User {

    public ConcreteUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println(this.name + " sends: " + message);
        mediator.showMessage(message, this);
    }

    @Override
    public void receive(String message, User sender) {
        System.out.println(this.name + " receives from " + sender.getName() + ": " + message);
    }
}
