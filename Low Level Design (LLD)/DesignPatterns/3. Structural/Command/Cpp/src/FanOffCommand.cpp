#include "../include/FanOffCommand.h"

FanOffCommand::FanOffCommand(Fan* f) : fan(f) {}

void FanOffCommand::execute() {
    fan->turnOff();
}
