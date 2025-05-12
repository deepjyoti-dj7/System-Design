public class RemoteControl {
    protected Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public void turnDeviceOn() {
        device.turnOn();
    }

    public void turnDeviceOff() {
        device.turnOff();
    }

    public void setDeviceVolume(int percent) {
        device.setVolume(percent);
    }
}
