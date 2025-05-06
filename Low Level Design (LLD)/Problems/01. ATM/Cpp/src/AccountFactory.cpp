#include "../include/AccountFactory.h"
#include "../include/SavingsAccount.h"
#include "../include/CurrentAccount.h"
#include <stdexcept>

Account* AccountFactory::createAccount(const std::string& type, const std::string& accNo, double balance) {
    if (type == "savings") return new SavingsAccount(accNo, balance);
    if (type == "current") return new CurrentAccount(accNo, balance);
    throw std::invalid_argument("Unknown account type");
}
