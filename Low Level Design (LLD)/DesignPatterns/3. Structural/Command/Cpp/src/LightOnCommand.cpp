#include "../include/LightOnCommand.h"

LightOnCommand::LightOnCommand(Light* l) : light(l) {}

void LightOnCommand::execute() {
    light->turnOn();
}
