package payment;

import utils.PaymentStatus;

public class Payment {
    private String id;
    private String orderId;
    private double amount;
    private PaymentStatus status;

    public Payment(String id, String orderId, double amount, PaymentStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
