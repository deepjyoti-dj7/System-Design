import DesignPatterns.AbstractFactoryPattern.Factory.ComputerFactory;
import DesignPatterns.AbstractFactoryPattern.Factory.PCFactory;
import DesignPatterns.AbstractFactoryPattern.Factory.ServerFactory;

import DesignPatterns.FactoryPattern.PC;
import DesignPatterns.FactoryPattern.Server;

import Problems.ATM.Account;
import Problems.ATM.ATM;
import Problems.ATM.ATMService;
import Problems.ATM.Bank;
import Problems.ATM.Card;
import Problems.ATM.SavingsAccount;
import Problems.ATM.User;

import Problems.ATM.Account;

public class Main {

    public static void main(String[] args) {
        // Call the test methods
        // testAbstractFactoryPattern();
        // testFactoryPattern();
        testATM();
    }

    public static void testAbstractFactoryPattern() {
        DesignPatterns.AbstractFactoryPattern.Computer pc = ComputerFactory.getComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
        DesignPatterns.AbstractFactoryPattern.Computer server = ComputerFactory.getComputer(new ServerFactory("10 GB", "1 TB", "2.9 GHz"));

        System.out.println("Abstract Factory PC Config::" + pc);
        System.out.println("Abstract Factory Server Config::" + server);
    }

    public static void testFactoryPattern() {
        DesignPatterns.FactoryPattern.Computer pc = new PC("4 GB", "1 TB", "2.5 GHz");
        DesignPatterns.FactoryPattern.Computer server = new Server("16 GB", "2 TB", "3.0 GHz");

        System.out.println("Factory Pattern PC Config::" + pc);
        System.out.println("Factory Pattern Server Config::" + server);
    }

    public static void testATM() {
        Card card = new Card("123456789", "1234");
        Account account = new SavingsAccount("ACC1001", 500.0);
        User user = new User("Alice", card, account);

        Bank bank = new Bank();
        bank.addUser(user);

        ATM atm = new ATM(1000.0);
        ATMService atmService = new ATMService(bank, atm);

        // Start session
        atmService.startSession("123456789", "1234");
    }
}