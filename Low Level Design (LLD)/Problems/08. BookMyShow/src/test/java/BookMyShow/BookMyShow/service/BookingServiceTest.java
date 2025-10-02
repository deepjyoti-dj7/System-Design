package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.BookingRequest;
import BookMyShow.BookMyShow.entity.Booking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Test
    public void testCreateBooking() {
        BookingRequest req = new BookingRequest();
        req.setUserId(1L);
        req.setShowId(1L);
        req.setSeatNumbers(Arrays.asList("A1","A2"));
        req.setPaymentToken("dummy");

        Booking booking = bookingService.createBooking(req.getUserId(), req.getShowId(), req.getSeatNumbers(), req.getPaymentToken());
        assert(booking.getStatus().equals("CONFIRMED"));
    }
}

