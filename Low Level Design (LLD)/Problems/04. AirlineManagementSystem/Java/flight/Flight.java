package flight;

import booking.Seat;
import booking.SeatFactory;
import utils.SeatStatus;

import java.util.List;
import java.util.stream.Collectors;

public class Flight {
    private String flightNumber;
    private Aircraft aircraft;
    private Route route;
    private Schedule schedule;
    private List<Seat> seats;

    public Flight(String flightNumber, Aircraft aircraft, Route route, Schedule schedule) {
        this.flightNumber = flightNumber;
        this.aircraft = aircraft;
        this.route = route;
        this.schedule = schedule;
        this.seats = SeatFactory.generateSeats(aircraft.getSeatConfig());
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream().filter(s -> s.getSeatStatus() == SeatStatus.AVAILABLE).collect(Collectors.toList());
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Seat getSeatByNumber(String seatNumber) {
        return seats.stream().filter(s -> s.getSeatNumber().equals(seatNumber)).findFirst().orElse(null);
    }

    public String getRoute() {
        return route.getSource() + " to " + route.getDestination();
    }

    public String getSchedule() {
        return schedule.getDepartureTime() + "->" + schedule.getArrivalTime();
    }
}
