#include "../include/PayPalPayment.h"
#include <iostream>

PayPalPayment::PayPalPayment(const std::string& email)
    : email(email) {}

void PayPalPayment::pay(int amount) {
    std::cout << "Paid $" << amount << " using PayPal [" << email << "]" << std::endl;
}
