package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.entity.Payment;
import BookMyShow.BookMyShow.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public boolean charge(String paymentToken, double amount, Long bookingId) {
        boolean success = paymentToken != null && !paymentToken.isBlank();

        Payment payment = Payment.builder()
                .bookingId(bookingId)
                .amount(amount)
                .status(success ? "SUCCESS" : "FAILED")
                .paymentMode("CARD") // default for stub
                .transactionId(UUID.randomUUID().toString())
                .paidAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        return success;
    }
}
