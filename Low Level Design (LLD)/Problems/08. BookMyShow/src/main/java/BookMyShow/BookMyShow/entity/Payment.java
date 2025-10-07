package BookMyShow.BookMyShow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    private double amount;

    private String status; // SUCCESS, FAILED, PENDING

    private String paymentMode; // CARD, UPI, WALLET

    private String transactionId;

    private LocalDateTime paidAt;
}
