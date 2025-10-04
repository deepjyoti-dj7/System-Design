package BookMyShow.BookMyShow.controller.user;

import BookMyShow.BookMyShow.entity.Ticket;
import BookMyShow.BookMyShow.repository.TicketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/booking/{bookingId}")
    public List<Ticket> getTicketsByBooking(@PathVariable Long bookingId) {
        return ticketRepository.findByBookingId(bookingId);
    }
}
