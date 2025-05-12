#include "../include/RemoteControl.h"

RemoteControl::RemoteControl(Device* dev) : device(dev) {}

void RemoteControl::turnDeviceOn() {
    device->turnOn();
}

void RemoteControl::turnDeviceOff() {
    device->turnOff();
}

void RemoteControl::setDeviceVolume(int percent) {
    device->setVolume(percent);
}
