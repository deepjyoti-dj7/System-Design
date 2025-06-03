#pragma once

#include <string>
#include "../../utils/PaymentStatus.h"

class Payment {
private:
    std::string id;
    std::string orderId;
    double amount;
    PaymentStatus status;

public:
    // Constructor
    Payment(const std::string& id, const std::string& orderId, double amount, PaymentStatus status);

    // Getters
    const std::string& getId() const;
    const std::string& getOrderId() const;
    double getAmount() const;
    PaymentStatus getStatus() const;

    // Setter
    void setStatus(PaymentStatus newStatus);
};
