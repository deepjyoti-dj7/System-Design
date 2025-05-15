#pragma once
#include "PaymentStrategy.h"

class PaymentService {
private:
    PaymentStrategy* strategy;

public:
    PaymentService();
    void setPaymentStrategy(PaymentStrategy* strategy);
    void processPayment(int amount);
};
