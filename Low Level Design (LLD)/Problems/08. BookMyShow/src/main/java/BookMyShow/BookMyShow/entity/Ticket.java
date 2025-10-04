package BookMyShow.BookMyShow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // lazy for performance
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY) // lazy for performance
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    private String seatNumber; // optional snapshot

    private BigDecimal price; // safer for money
}

