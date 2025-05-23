#include <iostream>

#include "./flight/include/Aircraft.h"
#include "./flight/include/Flight.h"
#include "./flight/include/Route.h"
#include "./flight/include/Schedule.h"
#include "./service/include/BookingService.h"
#include "./user/include/Passenger.h"
#include "./utils/Enums.cpp"

#include <map>
#include <string>
#include <sstream>
#include <iomanip>
#include <chrono>
#include <ctime>

std::chrono::system_clock::time_point parseDateTime(const std::string& datetime) {
    std::tm tm = {};
    std::istringstream ss(datetime);
    ss >> std::get_time(&tm, "%Y-%m-%d %H:%M");
    if (ss.fail()) {
        throw std::runtime_error("Failed to parse date-time string.");
    }
    std::time_t time = std::mktime(&tm);
    return std::chrono::system_clock::from_time_t(time);
}

int main() {
    // Setup aircraft
    std::map<SeatClass, int> seatMap;
    seatMap[SeatClass::ECONOMY] = 50;
    seatMap[SeatClass::BUSINESS] = 10;
    Aircraft aircraft("Boeing 737", 60, seatMap);

    // Setup flight
    Route route("New York", "London");

    // Simulate schedule with time strings (or your custom time handling)
    std::string departure = "2024-06-01 10:00";
    std::string arrival = "2024-06-01 16:00";

    auto departureTime = parseDateTime(departure);
    auto arrivalTime = parseDateTime(arrival);

    Schedule schedule(departureTime, arrivalTime);


    Flight flight("NYLON101", aircraft, route, schedule);

    // Passenger
    Passenger passenger("P1", "John Doe", "john@example.com", "1234567890");

    // Booking
    BookingService bookingService;
    try {
        bookingService.createBooking(&flight, &passenger, "E1");
    } catch (const std::runtime_error& e) {
        std::cerr << "Booking failed: " << e.what() << std::endl;
    }

    return 0;
}
