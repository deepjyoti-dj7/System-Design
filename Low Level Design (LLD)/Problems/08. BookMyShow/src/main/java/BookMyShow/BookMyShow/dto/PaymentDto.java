package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PaymentDto {

    private PaymentDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRequest {
        @NotNull(message = "Booking ID is required")
        private Long bookingId;

        @NotNull(message = "Amount is required")
        private Double amount;

        @NotBlank(message = "Payment token is required")
        private String paymentToken;

        private String paymentMode; // optional: CARD, UPI, etc.
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResponse {
        private Long paymentId;
        private Long bookingId;
        private Double amount;
        private String status; // SUCCESS, FAILED
        private String paymentMode;
        private String transactionId;
        private LocalDateTime paidAt;
    }
}
