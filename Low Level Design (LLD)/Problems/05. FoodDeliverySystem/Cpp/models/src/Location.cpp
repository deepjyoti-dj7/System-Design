#include "../include/Location.h"

Location::Location(double latitude, double longitude, const std::string& address)
    : latitude(latitude), longitude(longitude), address(address) {}

double Location::getLatitude() const {
    return latitude;
}

double Location::getLongitude() const {
    return longitude;
}

const std::string& Location::getAddress() const {
    return address;
}