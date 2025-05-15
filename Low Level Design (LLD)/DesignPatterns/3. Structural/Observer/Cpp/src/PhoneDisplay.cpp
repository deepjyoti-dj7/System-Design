#include "../include/PhoneDisplay.h"

PhoneDisplay::PhoneDisplay(Subject* subject) : subject(subject) {
    subject->addObserver(this);
}

void PhoneDisplay::update(float temperature) {
    std::cout << "Phone Display: Temperature updated to " << temperature << "°C\n";
}
