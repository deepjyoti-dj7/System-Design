package Problems.ATM;

public class User {
    private final String name;
    private final Card card;
    private final Account account;

    public User(String name, Card card, Account account) {
        this.name = name;
        this.card = card;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public Card getCard() {
        return card;
    }

    public Account getAccount() {
        return account;
    }
}
