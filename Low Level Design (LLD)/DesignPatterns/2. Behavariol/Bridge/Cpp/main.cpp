#include "./include/TV.h"
#include "./include/Radio.h"
#include "./include/RemoteControl.h"
#include "./include/AdvancedRemoteControl.h"

int main() {
    TV tv;
    RemoteControl tvRemote(&tv);
    tvRemote.turnDeviceOn();
    tvRemote.setDeviceVolume(50);
    tvRemote.turnDeviceOff();

    std::cout << std::endl;

    Radio radio;
    AdvancedRemoteControl radioRemote(&radio);
    radioRemote.turnDeviceOn();
    radioRemote.setDeviceVolume(70);
    radioRemote.mute();
    radioRemote.turnDeviceOff();

    return 0;
}
