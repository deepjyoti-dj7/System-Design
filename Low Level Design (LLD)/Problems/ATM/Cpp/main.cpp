#include <iostream>
#include "include/CardFactory.h"
#include "include/AccountFactory.h"
#include "include/User.h"
#include "include/Bank.h"
#include "include/ATM.h"
#include "include/ATMService.h"

int main() {
    Card* card = CardFactory::createCard("123456789", "1234");
    Account* account = AccountFactory::createAccount("savings", "ACC1001", 500.0);
    User* user = new User("Alice", card, account);

    Bank* bank = new Bank();
    bank->addUser(user);

    ATM* atm = new ATM(1000.0);
    ATMService atmService(bank, atm);

    atmService.startSession("123456789", "1234");

    delete user;
    delete bank;
    delete atm;

    return 0;
}
