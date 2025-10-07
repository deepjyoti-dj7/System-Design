package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.BookingDto.BookingRequest;
import BookMyShow.BookMyShow.dto.BookingDto.BookingResponse;
import BookMyShow.BookMyShow.entity.*;
import BookMyShow.BookMyShow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final SeatRepository seatRepository;
    private final SeatLockService seatLockService;
    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        List<Seat> seats = seatRepository.findByScreenIdAndSeatNumberIn(
                show.getScreen().getId(), request.getSeatNumbers()
        );
        if (seats.size() != request.getSeatNumbers().size())
            throw new RuntimeException("Some seats not found");

        // Step 1: Lock seats
        String sessionId = UUID.randomUUID().toString();
        List<Long> seatIds = seats.stream().map(Seat::getId).collect(Collectors.toList());
        seatLockService.lockSeats(seatIds, show.getId(), sessionId);

        // Step 2: Calculate total price and charge
        double total = seats.size() * show.getBasePrice();
        boolean paid = paymentService.charge(request.getPaymentToken(), total, request.getBookingId());

        // Step 3: Create booking
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .bookingTime(LocalDateTime.now())
                .totalPrice(total)
                .status(paid ? "CONFIRMED" : "INITIATED")
                .build();
        booking = bookingRepository.save(booking);

        // Step 4: Create tickets if payment successful
        if (paid) {
            for (Seat s : seats) {
                Ticket ticket = Ticket.builder()
                        .booking(booking)
                        .seat(s)
                        .seatNumber(s.getSeatNumber())
                        .price(BigDecimal.valueOf(show.getBasePrice()))
                        .build();
                ticketRepository.save(ticket);

                // Mark seat as booked
                s.setIsBooked(true);
                seatRepository.save(s);
            }
            seatLockService.unlockSeats(show.getId(), seatIds, sessionId);
        }

        return new BookingResponse(
                booking.getId(),
                booking.getStatus(),
                seats.stream().map(Seat::getSeatNumber).collect(Collectors.toList()),
                total
        );
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        // Unlock seats for cancelled booking
        List<Long> seatIds = booking.getTickets()
                .stream().map(t -> t.getSeat().getId()).toList();
        seatLockService.unlockSeats(booking.getShow().getId(), seatIds, booking.getId().toString());
    }
}
