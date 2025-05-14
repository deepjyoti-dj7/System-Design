#include "../include/RemoteControl.h"

void RemoteControl::setCommand(Command* c) {
    command = c;
}

void RemoteControl::pressButton() {
    if (command) command->execute();
}
