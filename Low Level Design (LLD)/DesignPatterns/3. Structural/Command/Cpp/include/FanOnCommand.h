#pragma once
#include "Command.h"
#include "Fan.h"

class FanOnCommand : public Command {
private:
    Fan* fan;

public:
    FanOnCommand(Fan* f);
    void execute() override;
};
