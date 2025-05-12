#pragma once
#include "Device.h"

class RemoteControl {
protected:
    Device* device;
public:
    RemoteControl(Device* dev);
    virtual void turnDeviceOn();
    virtual void turnDeviceOff();
    virtual void setDeviceVolume(int percent);
    virtual ~RemoteControl() = default;
};
