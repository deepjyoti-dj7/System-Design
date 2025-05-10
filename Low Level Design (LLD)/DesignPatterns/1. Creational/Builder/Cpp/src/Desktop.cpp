#include "../include/Desktop.hpp"
#include <iostream>

void Desktop::setMonitor(std::string aMonitor) {
    monitor = aMonitor;
}

void Desktop::setMouse(std::string aMouse) {
    mouse = aMouse;
}

void Desktop::setKeyboard(std::string aKeyboard) {
    keyboard = aKeyboard;
}

void Desktop::setSpeaker(std::string aSpeaker) {
    speaker = aSpeaker;
}

void Desktop::setRam(std::string aRam) {
    ram = aRam;
}

void Desktop::setProcessor(std::string aProcessor) {
    processor = aProcessor;
}

void Desktop::setMotherboard(std::string aMotherboard) {
    motherboard = aMotherboard;
}

void Desktop::showSpecifications() {
    std::cout << "----------------------------------------------" << std::endl;
    std::cout << "Monitor: " << monitor << std::endl;
    std::cout << "Mouse: " << mouse << std::endl;
    std::cout << "Keyboard: " << keyboard << std::endl;
    std::cout << "Speaker: " << speaker << std::endl;
    std::cout << "Ram: " << ram << std::endl;
    std::cout << "Processor: " << processor << std::endl;
    std::cout << "Motherboard: " << motherboard << std::endl;
    std::cout << "----------------------------------------------" << std::endl;
}
