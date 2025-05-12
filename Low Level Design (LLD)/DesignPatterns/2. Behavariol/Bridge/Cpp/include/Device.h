#pragma once

class Device {
public:
    virtual void turnOn() = 0;
    virtual void turnOff() = 0;
    virtual void setVolume(int percent) = 0;
    virtual ~Device() = default;
};
