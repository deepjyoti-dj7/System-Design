#pragma once
#include "LightningPort.h"
#include "USBCCharger.h"

class USBCToLightningAdapter : public LightningPort {
private:
    USBCCharger* usbcCharger;

public:
    USBCToLightningAdapter(USBCCharger* charger);
    void connectWithLightning() override;
};
