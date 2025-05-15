#pragma once
#include "PaymentStrategy.h"
#include <string>

class BitcoinPayment : public PaymentStrategy {
private:
    std::string walletAddress;

public:
    BitcoinPayment(const std::string& walletAddress);
    void pay(int amount) override;
};
