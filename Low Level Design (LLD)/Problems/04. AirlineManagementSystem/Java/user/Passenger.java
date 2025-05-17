package user;

import utils.UserRole;
import booking.Booking;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends User {
    private List<Booking> bookings;

    public Passenger(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = UserRole.PASSENGER;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getEmail() {
        return email;
    }
}


