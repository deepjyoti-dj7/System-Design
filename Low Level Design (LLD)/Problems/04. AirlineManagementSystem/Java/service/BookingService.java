package service;

import booking.Booking;
import booking.Seat;
import payment.Payment;
import user.Passenger;
import flight.Flight;
import utils.SeatStatus;

import java.util.UUID;

public class BookingService {
    public Booking createBooking(Flight flight, Passenger passenger, String seatNumber) {
        Seat seat = flight.getSeatByNumber(seatNumber);
        if (seat == null || seat.getSeatStatus() == SeatStatus.BOOKED) {
            throw new RuntimeException("Seat not available");
        }

        Booking booking = new Booking(UUID.randomUUID().toString(), flight, passenger, seat);
        Payment payment = new Payment(UUID.randomUUID().toString(), 100.0);

        booking.confirmBooking(payment);
        passenger.addBooking(booking);

        new NotificationService().sendBookingConfirmation(passenger, booking, flight, seat);

        return booking;
    }
}
