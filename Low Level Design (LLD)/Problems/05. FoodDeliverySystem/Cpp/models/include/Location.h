#pragma once

#include <string>

class Location {
private:
    double latitude;
    double longitude;
    std::string address;

public:
    Location(const double latitude, const double longitude, const std::string& address);

    double getLatitude() const;
    double getLongitude() const;
    const std::string& getAddress() const;
};