#pragma once

class ATM {
private:    
    double availableCash;
public:
    ATM(double initialCash);
    bool dispenseCash(double amount);
    void loadCash(double amount);
    double getAvailableCash() const;
};