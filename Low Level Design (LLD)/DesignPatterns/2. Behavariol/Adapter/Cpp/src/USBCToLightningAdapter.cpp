#include <iostream>
#include "../include/USBCToLightningAdapter.h"

USBCToLightningAdapter::USBCToLightningAdapter(USBCCharger* charger) : usbcCharger(charger) {}

void USBCToLightningAdapter::connectWithLightning() {
    std::cout << "Using USB-C to Lightning adapter..." << std::endl;
    usbcCharger->connectWithUSBC();
}

