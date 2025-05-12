#pragma once
#include "RemoteControl.h"
#include <iostream>

class AdvancedRemoteControl : public RemoteControl {
public:
    AdvancedRemoteControl(Device* dev);
    void mute();
};
