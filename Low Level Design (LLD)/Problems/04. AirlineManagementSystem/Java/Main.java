import flight.Aircraft;
import flight.Flight;
import flight.Route;
import flight.Schedule;
import service.BookingService;
import user.Passenger;
import utils.SeatClass;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Setup aircraft
        Map<SeatClass, Integer> seatMap = new HashMap<>();
        seatMap.put(SeatClass.ECONOMY, 50);
        seatMap.put(SeatClass.BUSINESS, 10);
        Aircraft aircraft = new Aircraft("Boeing 737", 60, seatMap);

        // Setup flight
        Route route = new Route("New York", "London");
        Schedule schedule = new Schedule(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(6));
        Flight flight = new Flight("NYLON101", aircraft, route, schedule);

        // Passenger
        Passenger passenger = new Passenger("P1", "John Doe", "john@example.com", "1234567890");

        // Booking
        BookingService bookingService = new BookingService();
        bookingService.createBooking(flight, passenger, "E1");
    }
}
