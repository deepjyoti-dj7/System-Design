package Problems.ATM;

public class ATMService {
    private final Bank bank;
    private final ATM atm;

    public ATMService(Bank bank, ATM atm) {
        this.bank = bank;
        this.atm = atm;
    }

    public void startSession(String cardNumber, String pin) {
        User user = bank.authenticate(cardNumber, pin);
        if (user == null) {
            System.out.println("Authentication Failed!");
            return;
        }

        Account account = user.getAccount();

        System.out.println("Welcome, " + user.getName() + "!");
        System.out.println("\nAvailable Balance: Rs." + account.getBalance());

        double withdrawAmount = 100.0;
        if (account.withdraw(withdrawAmount) && atm.dispenseCash(withdrawAmount)) {
            System.out.println("\nWithdrawn: Rs." + withdrawAmount);
        } else {
            System.out.println("\nWithdrawal failed!");
        }

        System.out.println("Balance after withdrawal: Rs." + account.getBalance());

        double depositAmount = 50.0;
        account.deposit(depositAmount);
        System.out.println("\nDeposited: Rs." + depositAmount);

        System.out.println("Balance after deposit: Rs." + account.getBalance());
    }
}
