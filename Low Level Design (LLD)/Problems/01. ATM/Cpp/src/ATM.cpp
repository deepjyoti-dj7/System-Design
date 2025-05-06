#include "../include/ATM.h"

ATM::ATM(double initialCash) : availableCash(initialCash) {};

bool ATM::dispenseCash(double amount) {
    if (amount <= availableCash) {
        availableCash -= amount;
        return true;
    }
    return false;
}

void ATM::loadCash(double amount) {
    availableCash += amount;
}

double ATM::getAvailableCash() const {
   return availableCash; 
}
