package Problems.ATM;

public class Transaction {
    public enum Type { WITHDRAW, DEPOSIT, BALANCE_INQUIRY }

    private final Type type;
    private final double amount;

    public Transaction(Type type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return "Transaction: " + type + ", Amount: " + amount;
    }
}
