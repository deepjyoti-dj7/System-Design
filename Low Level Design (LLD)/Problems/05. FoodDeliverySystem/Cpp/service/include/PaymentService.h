#pragma once

#include <unordered_map>
#include <string>
#include "../../payment/include/Payment.h"
#include "../../utils/include/PaymentStatus.h"

class PaymentService {
private:
    std::unordered_map<std::string, Payment> payments;

public:
    void processPayment(Payment payment);
    const Payment* getPaymentById(const std::string& id) const;
};