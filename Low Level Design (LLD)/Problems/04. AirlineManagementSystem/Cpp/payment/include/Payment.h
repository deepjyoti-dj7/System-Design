#pragma once
#include <string>
#include "../../utils/Enums.cpp"

class Payment {
private:
    std::string paymentId;
    double amount;
    PaymentStatus status;

public:
    Payment(const std::string& paymentId, double amount);

    std::string getPaymentId() const;
    double getAmount() const;
    PaymentStatus getStatus() const;
    void setStatus(PaymentStatus newStatus);
};
