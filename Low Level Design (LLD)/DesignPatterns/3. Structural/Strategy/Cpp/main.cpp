#include "include/PaymentService.h"
#include "include/CreditCardPayment.h"
#include "include/PayPalPayment.h"
#include "include/BitcoinPayment.h"

int main() {
    PaymentService service;

    CreditCardPayment creditCard("1234-5678-9012-3456");
    service.setPaymentStrategy(&creditCard);
    service.processPayment(250);

    PayPalPayment paypal("john@example.com");
    service.setPaymentStrategy(&paypal);
    service.processPayment(150);

    BitcoinPayment bitcoin("1A2B3C4D5E6F");
    service.setPaymentStrategy(&bitcoin);
    service.processPayment(100);

    return 0;
}
