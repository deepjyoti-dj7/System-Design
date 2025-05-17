package service;

import booking.Booking;
import booking.Seat;
import flight.Flight;
import flight.Route;
import flight.Schedule;
import user.Passenger;

public class NotificationService {
    public void sendBookingConfirmation(Passenger passenger, Booking booking, Flight flight, Seat seat) {
        System.out.println("Email: " + passenger.getEmail()
                + "\n" + "Booking id: " + booking.getBookingId()
                + "\n" + "Flight: " + flight.getFlightNumber()
                + "\n" + "Seat No: " + seat.getSeatNumber()
                + "\n" + "Route: " + flight.getRoute()
                + "\n" + "Schedule: " + flight.getSchedule());
    }
}
