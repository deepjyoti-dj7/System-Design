package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.entity.*;
import BookMyShow.BookMyShow.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final SeatRepository seatRepository;
    private final SeatLockRepository seatLockRepository;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public BookingService(SeatRepository seatRepository,
                          SeatLockRepository seatLockRepository,
                          ShowRepository showRepository,
                          BookingRepository bookingRepository,
                          TicketRepository ticketRepository,
                          UserRepository userRepository,
                          PaymentService paymentService) {
        this.seatRepository = seatRepository;
        this.seatLockRepository = seatLockRepository;
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public Booking createBooking(Long userId, Long showId, List<String> seatNumbers, String paymentToken) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seats = seatRepository.findByScreenIdAndSeatNumberIn(show.getScreen().getId(), seatNumbers);
        if (seats.size() != seatNumbers.size()) throw new RuntimeException("Some seats not found");

        LocalDateTime now = LocalDateTime.now();
        for (Seat s : seats) {
            boolean locked = seatLockRepository.existsBySeatIdAndShowIdAndExpiresAtAfter(s.getId(), showId, now);
            if (locked) throw new RuntimeException("Seat already locked: " + s.getSeatNumber());
        }

        String sessionId = UUID.randomUUID().toString();
        LocalDateTime expiresAt = now.plusMinutes(5);
        for (Seat s : seats) {
            SeatLock lock = SeatLock.builder()
                    .seatId(s.getId())
                    .showId(showId)
                    .sessionId(sessionId)
                    .lockedAt(now)
                    .expiresAt(expiresAt)
                    .build();
            seatLockRepository.save(lock);
        }

        double total = seats.size() * show.getBasePrice();
        boolean paid = paymentService.charge(paymentToken, total);

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .bookingTime(now)
                .totalPrice(total)
                .status(paid ? "CONFIRMED" : "INITIATED")
                .build();
        booking = bookingRepository.save(booking);

        if (paid) {
            for (Seat s : seats) {
                Ticket ticket = Ticket.builder()
                        .booking(booking)
                        .seat(s)
                        .seatNumber(s.getSeatNumber())
                        .price(show.getBasePrice())
                        .build();
                ticketRepository.save(ticket);
            }
            seatLockRepository.deleteBySessionId(sessionId);
        }

        return booking;
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
}
