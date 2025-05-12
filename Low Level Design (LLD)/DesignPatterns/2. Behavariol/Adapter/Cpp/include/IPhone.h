#pragma once
#include "LightningPort.h"

class IPhone {
private:
    LightningPort* lightningPort;

public:
    IPhone(LightningPort* port);
    void charge();
};
