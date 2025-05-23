#include "../include/Aircraft.h"

Aircraft::Aircraft(const std::string& model, const int& capacity, const std::map<SeatClass, int>& seatConfig) 
    : model(model), capacity(capacity), seatConfig(seatConfig) {}

const std::map<SeatClass, int> Aircraft::getSeatConfig() const {
    return seatConfig;
}