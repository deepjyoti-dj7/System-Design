#include "../include/Payment.h"

Payment::Payment(const std::string& paymentId, double amount)
    : paymentId(paymentId), amount(amount), status(PaymentStatus::SUCCESS) {}

std::string Payment::getPaymentId() const {
    return paymentId;
}

double Payment::getAmount() const {
    return amount;
}

PaymentStatus Payment::getStatus() const {
    return status;
}

void Payment::setStatus(PaymentStatus newStatus) {
    status = newStatus;
}