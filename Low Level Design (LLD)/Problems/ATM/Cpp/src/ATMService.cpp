#include <iostream>
#include "../include/ATMService.h"

ATMService::ATMService(Bank* bank, ATM* atm) : bank(bank), atm(atm) {};

void ATMService::startSession(const std::string& cardNumber, const std::string& pin) {
    User* user = bank->authenticate(cardNumber, pin);
    if (!user) {
        std::cout << "Authentication Failed!\n";
        return;
    }

    Account* account = user->getAccount();
    std::cout << "Welcome!" << user->getName() << "\n";
    std::cout << "Initial Balance: $" << account->getBalance() << "\n";

    double withdrawAmount = 100.0;
    if (account->withdraw(withdrawAmount) && atm->dispenseCash(withdrawAmount)) {
        std::cout << "Withdrawn: Rs." << withdrawAmount << "\n";
    } else {
        std::cout << "Withdrawal failed!\n";
    }

    double depositAmount = 50.0;
    account->deposit(depositAmount);
    std::cout << "Deposited: Rs." << depositAmount << "\n";
    std::cout << "Final Balance: Rs." << account->getBalance() << "\n";
}