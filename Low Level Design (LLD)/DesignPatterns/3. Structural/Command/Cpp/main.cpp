#include "include/Light.h"
#include "include/Fan.h"
#include "include/LightOnCommand.h"
#include "include/LightOffCommand.h"
#include "include/FanOnCommand.h"
#include "include/FanOffCommand.h"
#include "include/RemoteControl.h"

int main() {
    Light light;
    Fan fan;

    LightOnCommand lightOn(&light);
    LightOffCommand lightOff(&light);
    FanOnCommand fanOn(&fan);
    FanOffCommand fanOff(&fan);

    RemoteControl remote;

    remote.setCommand(&lightOn);
    remote.pressButton();

    remote.setCommand(&fanOn);
    remote.pressButton();

    remote.setCommand(&lightOff);
    remote.pressButton();

    remote.setCommand(&fanOff);
    remote.pressButton();

    return 0;
}
