#include "../include/FanOnCommand.h"

FanOnCommand::FanOnCommand(Fan* f) : fan(f) {}

void FanOnCommand::execute() {
    fan->turnOn();
}
