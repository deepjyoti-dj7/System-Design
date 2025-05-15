public class PaymentService {
    private PaymentStrategy strategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(int amount) {
        if (strategy == null) {
            System.out.println("Please set a payment strategy.");
            return;
        }
        strategy.pay(amount);
    }
}
