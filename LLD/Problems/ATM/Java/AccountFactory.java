package Problems.ATM;

public class AccountFactory {
    public static Account createAccount(String type, String accountNumber, double balance) {
        if ("savings".equalsIgnoreCase(type)) {
            return new SavingsAccount(accountNumber, balance);
        } else if ("current".equalsIgnoreCase(type)) {
            return new CurrentAccount(accountNumber, balance);
        } else {
            throw new IllegalArgumentException("Invalid account type: " + type);
        }
    }
}
