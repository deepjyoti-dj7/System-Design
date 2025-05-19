#pragma once
#include <chrono>

class Schedule {
private:
    std::chrono::system_clock::time_point departureTime;
    std::chrono::system_clock::time_point arrivalTime;

public:
    Schedule(const std::chrono::system_clock::time_point& departureTime,
             const std::chrono::system_clock::time_point& arrivalTime);

    const std::chrono::system_clock::time_point& getDepartureTime() const;
    const std::chrono::system_clock::time_point& getArrivalTime() const;
};
