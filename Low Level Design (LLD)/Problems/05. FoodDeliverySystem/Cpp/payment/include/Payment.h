#pragma once

#include <string>
#include "../../utils/include/PaymentStatus.h"

class Payment {
private:
    std::string id;
    std::string orderId;
    double amount;
    PaymentStatus status;

public:
    Payment();
    Payment(const std::string& id, const std::string& orderId, double amount, PaymentStatus status);

    const std::string& getId() const;
    const std::string& getOrderId() const;
    double getAmount() const;
    PaymentStatus getStatus() const;

    void setStatus(PaymentStatus newStatus);
};
