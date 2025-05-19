#include "../include/Aircraft.h"

Aircraft::Aircraft(std::string& model, int& capacity, std::map<SeatClass, int>& seatConfig) 
    : model(model), capacity(capacity), seatConfig(seatConfig) {}

const std::map<SeatClass, int> Aircraft::getSeatConfig() const {
    return seatConfig;
}