#pragma once
#include "Subject.h"

class WeatherStation : public Subject {
private:
    std::vector<Observer*> observers;
    float temperature;

public:
    void setTemperature(float temp);
    float getTemperature() const;

    void addObserver(Observer* observer) override;
    void removeObserver(Observer* observer) override;
    void notifyObservers() override;
};
