#include "../include/Schedule.h"

Schedule::Schedule(const std::chrono::system_clock::time_point& departureTime,
                   const std::chrono::system_clock::time_point& arrivalTime)
    : departureTime(departureTime), arrivalTime(arrivalTime) {}

const std::chrono::system_clock::time_point& Schedule::getDepartureTime() const {
    return departureTime;
}

const std::chrono::system_clock::time_point& Schedule::getArrivalTime() const {
    return arrivalTime;
}
