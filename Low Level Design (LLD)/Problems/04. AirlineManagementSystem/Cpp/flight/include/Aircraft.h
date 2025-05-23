#pragma once
#include <string>
#include <map>
#include "../../utils/Enums.cpp"

class Aircraft {
private:
    std::string model;
    int capacity;
    std::map<SeatClass, int> seatConfig;

public:
    Aircraft(const std::string& model, const int& capacity, const std::map<SeatClass, int>& seatConfig);

    const std::map<SeatClass, int> getSeatConfig() const;
};