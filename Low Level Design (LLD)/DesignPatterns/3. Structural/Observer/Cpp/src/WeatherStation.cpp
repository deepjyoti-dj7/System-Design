#include "../include/WeatherStation.h"
#include <algorithm>

void WeatherStation::setTemperature(float temp) {
    temperature = temp;
    notifyObservers();
}

float WeatherStation::getTemperature() const {
    return temperature;
}

void WeatherStation::addObserver(Observer* observer) {
    observers.push_back(observer);
}

void WeatherStation::removeObserver(Observer* observer) {
    observers.erase(std::remove(observers.begin(), observers.end(), observer), observers.end());
}

void WeatherStation::notifyObservers() {
    for (Observer* obs : observers) {
        obs->update(temperature);
    }
}
