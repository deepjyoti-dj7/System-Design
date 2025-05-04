public class Main {

    public static void main(String[] args) {
        testATM();
    }

    public static void testATM() {
        Card card = CardFactory.createCard("123456789", "1234");
        Account account = AccountFactory.createAccount("savings", "ACC1001", 500.0);
        User user = new User("Alice", card, account);

        Bank bank = new Bank();
        bank.addUser(user);

        ATM atm = new ATM(1000.0);
        ATMService atmService = new ATMService(bank, atm);

        // Start session
        atmService.startSession("123456789", "1234");
    }