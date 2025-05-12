#include <iostream>
#include "../include/IPhone.h"

IPhone::IPhone(LightningPort* port) : lightningPort(port) {}

void IPhone::charge() {
    lightningPort->connectWithLightning();
    std::cout << "iPhone is charging..." << std::endl;
}
