import java.util.ArrayList;
import java.util.List;

public class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();

    @Override
    public void showMessage(String message, User sender) {
        for (User user : users) {
            // Don't send message to the sender
            if (user != sender) {
                user.receive(message, sender);
            }
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
