#include "../include/AdvancedRemoteControl.h"

AdvancedRemoteControl::AdvancedRemoteControl(Device* dev) : RemoteControl(dev) {}

void AdvancedRemoteControl::mute() {
    std::cout << "Muting the device" << std::endl;
    device->setVolume(0);
}
