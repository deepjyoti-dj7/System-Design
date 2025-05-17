package service;

import booking.Booking;
import user.Passenger;

public class NotificationService {
    public void sendBookingConfirmation(Passenger passenger, Booking booking) {
        System.out.println("Email sent to " + passenger.getEmail() + ": Booking confirmed for " + booking.getBookingId());
    }
}
