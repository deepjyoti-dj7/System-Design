#pragma once
#include <iostream>
#include "Device.h"

class Radio : public Device {
public:
    void turnOn() override;
    void turnOff() override;
    void setVolume(int percent) override;
};
