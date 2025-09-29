package BookMyShow.BookMyShow.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean charge(String paymentToken, double amount) {
        return paymentToken != null && !paymentToken.isBlank();
    }
}
