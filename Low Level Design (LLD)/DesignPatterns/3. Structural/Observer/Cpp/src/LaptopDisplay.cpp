#include "../include/LaptopDisplay.h"

LaptopDisplay::LaptopDisplay(Subject* subject) : subject(subject) {
    subject->addObserver(this);
}

void LaptopDisplay::update(float temperature) {
    std::cout << "Laptop Display: Temperature updated to " << temperature << "°C\n";
}
