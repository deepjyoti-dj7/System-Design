#include "../include/SavingsAccount.h"

SavingsAccount::SavingsAccount(const std::string& accNo, double bal)
    : Account(accNo, bal) {}

std::string SavingsAccount::getType() const {
    return "Savings";
}
