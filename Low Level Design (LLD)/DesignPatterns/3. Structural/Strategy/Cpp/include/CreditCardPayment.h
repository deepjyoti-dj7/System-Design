#pragma once
#include "PaymentStrategy.h"
#include <string>

class CreditCardPayment : public PaymentStrategy {
private:
    std::string cardNumber;

public:
    CreditCardPayment(const std::string& cardNumber);
    void pay(int amount) override;
};
