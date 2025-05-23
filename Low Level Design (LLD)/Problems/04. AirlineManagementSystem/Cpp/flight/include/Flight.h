#pragma once
#include "Aircraft.h"
#include "Route.h"
#include "Schedule.h"
#include "../../booking/include/Seat.h"
#include "../../utils/Enums.cpp"
#include <vector>
#include <string>

class Flight {
private:
    std::string flightNumber;
    Aircraft aircraft;
    Route route;
    Schedule schedule;
    std::vector<Seat> seats;

public:
    Flight(const std::string& flightNumber, const Aircraft& aircraft, const Route& route, const Schedule& schedule);

    std::vector<Seat> getAvailableSeats() const;
    const std::string& getFlightNumber() const;
    const Seat* getSeatByNumber(const std::string& seatNumber) const;
    std::string getRoute() const;
    std::string getSchedule() const;
};