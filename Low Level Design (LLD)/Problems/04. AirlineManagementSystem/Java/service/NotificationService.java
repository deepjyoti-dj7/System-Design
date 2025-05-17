package service;

import booking.Booking;
import booking.Seat;
import flight.Flight;
import user.Passenger;

public class NotificationService {
    public void sendBookingConfirmation(Passenger passenger, Booking booking, Flight flight, Seat seat) {
        System.out.println("Email: " + passenger.getEmail()
                + "\n" + "Booking id: " + booking.getBookingId()
                + "\n" + "Flight: " + flight.getFlightNumber()
                + "\n" + "Seat No: " + seat.getSeatNumber());
    }
}
