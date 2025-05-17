package booking;

import flight.Flight;
import user.Passenger;
import utils.BookingStatus;
import payment.Payment;

public class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private Seat seat;
    private BookingStatus bookingStatus;
    private Payment payment;

    public Booking(String bookingId, Flight flight, Passenger passenger, Seat seat) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.bookingStatus = BookingStatus.PENDING;
    }

    public void confirmBooking(Payment payment) {
        this.bookingStatus = BookingStatus.CONFIRMED;
        this.payment = payment;
        seat.book();
    }

    public String getBookingId() {
        return bookingId;
    }
}
