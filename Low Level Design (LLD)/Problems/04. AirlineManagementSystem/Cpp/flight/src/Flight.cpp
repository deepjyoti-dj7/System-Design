#include "../include/Flight.h"
#include "../../booking/include/SeatFactory.h"
#include <sstream>
#include <iomanip>

Flight::Flight(const std::string& flightNumber, const Aircraft& aircraft,
               const Route& route, const Schedule& schedule)
    : flightNumber(flightNumber), aircraft(aircraft), route(route), schedule(schedule)
{
    seats = SeatFactory::generateSeats(aircraft.getSeatConfig());
}

std::vector<Seat> Flight::getAvailableSeats() const {
    std::vector<Seat> available;
    for (const auto& seat : seats) {
        if (seat.getSeatStatus() == SeatStatus::AVAILABLE) {
            available.push_back(seat);
        }
    }
    return available;
}

std::string Flight::getFlightNumber() const {
    return flightNumber;
}

Seat* Flight::getSeatByNumber(const std::string& seatNumber) {
    for (auto& seat : seats) {
        if (seat.getSeatNumber() == seatNumber) {
            return &seat;
        }
    }
    return nullptr;
}

std::string Flight::getRoute() const {
    return route.getSource() + " to " + route.getDestination();
}

std::string Flight::getSchedule() const {
    auto formatTime = [](const std::chrono::system_clock::time_point& time) -> std::string {
        std::time_t t = std::chrono::system_clock::to_time_t(time);
        std::ostringstream oss;
        oss << std::put_time(std::localtime(&t), "%Y-%m-%d %H:%M");
        return oss.str();
    };

    return formatTime(schedule.getDepartureTime()) + " -> " + formatTime(schedule.getArrivalTime());
}
