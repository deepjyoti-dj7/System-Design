#include "../include/PaymentService.h"
#include <iostream>

void PaymentService::processPayment(Payment payment) {
    payments[payment.getId()] = payment;
    std::cout << "Payment processing...\nDone!" << std::endl;
    payments[payment.getId()].setStatus(PaymentStatus::COMPLETED);
}

const Payment* PaymentService::getPaymentById(const std::string& id) const {
    auto it = payments.find(id);
    if (it != payments.end()) {
        return &it->second;
    }
    return nullptr;
}