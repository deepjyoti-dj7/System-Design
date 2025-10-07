package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.dto.TicketDto.TicketResponse;
import BookMyShow.BookMyShow.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketRepository ticketRepository;

    @GetMapping("/booking/{bookingId}")
    public List<TicketResponse> getTicketsByBooking(@PathVariable Long bookingId) {
        return ticketRepository.findByBookingId(bookingId)
                .stream()
                .map(ticket -> new TicketResponse(
                        ticket.getId(),
                        ticket.getBooking().getId(),
                        ticket.getSeatNumber(),
                        ticket.getPrice().doubleValue()
                ))
                .collect(Collectors.toList());
    }
}
