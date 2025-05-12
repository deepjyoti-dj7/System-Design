#include "../include/Projector.h"
#include <iostream>

void Projector::on() {
    std::cout << "Projector is ON" << std::endl;
}

void Projector::setInput(const std::string& input) {
    std::cout << "Projector input set to " << input << std::endl;
}

void Projector::off() {
    std::cout << "Projector is OFF" << std::endl;
}
