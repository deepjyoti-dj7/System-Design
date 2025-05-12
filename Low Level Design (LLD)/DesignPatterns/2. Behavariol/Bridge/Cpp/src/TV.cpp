#include "../include/TV.h"

void TV::turnOn() {
    std::cout << "TV is ON" << std::endl;
}

void TV::turnOff() {
    std::cout << "TV is OFF" << std::endl;
}

void TV::setVolume(int percent) {
    std::cout << "TV volume set to " << percent << "%" << std::endl;
}
