public class Main {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatRoom();

        User user1 = new ConcreteUser(mediator, "Alice");
        User user2 = new ConcreteUser(mediator, "Bob");
        User user3 = new ConcreteUser(mediator, "Charlie");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        user1.send("Hi everyone!");
        user2.send("Hello Alice!");
    }
}
