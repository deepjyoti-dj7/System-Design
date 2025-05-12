#include "../include/Radio.h"

void Radio::turnOn() {
    std::cout << "Radio is ON" << std::endl;
}

void Radio::turnOff() {
    std::cout << "Radio is OFF" << std::endl;
}

void Radio::setVolume(int percent) {
    std::cout << "Radio volume set to " << percent << "%" << std::endl;
}
