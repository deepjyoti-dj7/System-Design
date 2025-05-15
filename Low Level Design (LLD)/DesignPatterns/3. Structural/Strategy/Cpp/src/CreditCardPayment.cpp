#include "../include/CreditCardPayment.h"
#include <iostream>

CreditCardPayment::CreditCardPayment(const std::string& cardNumber)
    : cardNumber(cardNumber) {}

void CreditCardPayment::pay(int amount) {
    std::cout << "Paid $" << amount << " using Credit Card [" << cardNumber << "]" << std::endl;
}
