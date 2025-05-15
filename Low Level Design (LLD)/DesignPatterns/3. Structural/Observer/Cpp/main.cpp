#include "include/WeatherStation.h"
#include "include/PhoneDisplay.h"
#include "include/LaptopDisplay.h"

int main() {
    WeatherStation station;

    PhoneDisplay phoneDisplay(&station);
    LaptopDisplay laptopDisplay(&station);

    station.setTemperature(25.0f);
    station.setTemperature(30.5f);

    return 0;
}
