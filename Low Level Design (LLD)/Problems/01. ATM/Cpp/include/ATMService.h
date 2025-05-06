#pragma once
#include <string>
#include "Bank.h"
#include "ATM.h"

class ATMService {
private:
    Bank* bank;
    ATM* atm;

public:
    ATMService(Bank* bank, ATM* atm);
    void startSession(const std::string& cardNumber, const std::string& pin);
};