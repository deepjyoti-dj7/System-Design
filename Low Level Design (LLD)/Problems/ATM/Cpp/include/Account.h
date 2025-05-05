#pragma once
#include <string>

class Account {
protected:
    std::string accountNumber;
    double balance;

public:
    Account(const std::string& accountNumber, double balance);
    virtual ~Account() = default;
    virtual std::string getType() const = 0;
    
    std::string getAccountNumber();
    double getBalance() const;
    void deposit(double amount);
    bool withdraw(double amount);
};