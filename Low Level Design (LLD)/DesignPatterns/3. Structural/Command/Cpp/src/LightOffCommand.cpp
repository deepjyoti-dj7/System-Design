#include "../include/LightOffCommand.h"

LightOffCommand::LightOffCommand(Light* l) : light(l) {}

void LightOffCommand::execute() {
    light->turnOff();
}
