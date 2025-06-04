#include "../include/Payment.h"

Payment::Payment() 
    : id(""), orderId(""), amount(0.0), status(PaymentStatus::PENDING) {}

Payment::Payment(const std::string& id, const std::string& orderId, double amount, PaymentStatus status)
    : id(id), orderId(orderId), amount(amount), status(status) {}

const std::string& Payment::getId() const {
    return id;
}

const std::string& Payment::getOrderId() const {
    return orderId;
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
