#pragma once
#include "Account.h"
#include <string>

class AccountFactory {
public:
    static Account* createAccount(const std::string& type, const std::string& accNo, double balance);
};
