#pragma once
#include "Command.h"

class RemoteControl {
private:
    Command* command;

public:
    void setCommand(Command* c);
    void pressButton();
};
