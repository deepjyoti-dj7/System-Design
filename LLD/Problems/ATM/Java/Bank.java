import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getCard().getCardNumber(), user);
    }

    public User authenticate(String cardNumber, String pin) {
        User user = users.get(cardNumber);
        if (user != null && user.getCard().validatePin(pin)) {
            return user;
        }
        return null;
    }
}
