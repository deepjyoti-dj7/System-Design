public class Main {
    public static void main(String[] args) {
        PaymentService service = new PaymentService();

        service.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        service.processPayment(250);

        service.setPaymentStrategy(new PayPalPayment("john@example.com"));
        service.processPayment(150);

        service.setPaymentStrategy(new BitcoinPayment("1A2B3C4D5E6F"));
        service.processPayment(100);
    }
}
