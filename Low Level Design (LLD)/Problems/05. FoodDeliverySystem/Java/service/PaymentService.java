package service;

import payment.Payment;
import utils.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {
    private Map<String, Payment> payments = new HashMap<>();

    public void processPayment(Payment payment) {
        payments.put(payment.getId(), payment);
        System.out.println("Payment processing...\nDone!");
        payment.setStatus(PaymentStatus.COMPLETED);
    }

    public Payment getPaymentById(String id) {
        return payments.get(id);
    }
}
