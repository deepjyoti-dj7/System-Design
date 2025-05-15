#pragma once
#include "PaymentStrategy.h"
#include <string>

class PayPalPayment : public PaymentStrategy {
private:
    std::string email;

public:
    PayPalPayment(const std::string& email);
    void pay(int amount) override;
};
