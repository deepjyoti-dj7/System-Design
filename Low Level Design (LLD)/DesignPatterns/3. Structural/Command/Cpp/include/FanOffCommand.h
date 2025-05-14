#pragma once
#include "Command.h"
#include "Fan.h"

class FanOffCommand : public Command {
private:
    Fan* fan;

public:
    FanOffCommand(Fan* f);
    void execute() override;
};
