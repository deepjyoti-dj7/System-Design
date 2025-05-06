#include "../include/CurrentAccount.h"

CurrentAccount::CurrentAccount(const std::string& accNo, double bal)
    : Account(accNo, bal) {}

std::string CurrentAccount::getType() const {
    return "Current";
}
