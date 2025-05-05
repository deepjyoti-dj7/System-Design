#pragma once
#include "Account.h"
#include <string>

class CurrentAccount : public Account {
public:
    CurrentAccount(const std::string& accountNumber, double balance);
    std::string getType() const override;
};