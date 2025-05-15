#include "../include/PaymentService.h"
#include <iostream>

PaymentService::PaymentService() : strategy(nullptr) {}

void PaymentService::setPaymentStrategy(PaymentStrategy* strategy) {
    this->strategy = strategy;
}

void PaymentService::processPayment(int amount) {
    if (strategy)
        strategy->pay(amount);
    else
        std::cout << "No payment strategy set." << std::endl;
}
