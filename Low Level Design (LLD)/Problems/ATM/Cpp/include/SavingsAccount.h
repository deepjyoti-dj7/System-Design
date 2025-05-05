#pragma once
#include "Account.h"
#include <string>

class SavingsAccount : public Account {
public:
    SavingsAccount(const std::string& accountNumber, double balance);
    std::string getType() const override;
};