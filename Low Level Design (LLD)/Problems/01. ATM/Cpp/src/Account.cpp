#include "../include/Account.h"

Account::Account(const std::string& accNo, double bal)
    : accountNumber(accNo), balance(bal) {}

std::string Account::getAccountNumber() {
    return accountNumber;
}

double Account::getBalance() const {
    return balance;
}

void Account::deposit(double amount) {
    balance += amount;
}

bool Account::withdraw(double amount) {
    if (amount <= balance) {
        balance -= amount;
        return true;
    }
    return false;
}