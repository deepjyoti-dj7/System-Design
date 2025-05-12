public class Main {
    public static void main(String[] args) {
        Device tv = new TV();
        RemoteControl tvRemote = new RemoteControl(tv);
        tvRemote.turnDeviceOn();
        tvRemote.setDeviceVolume(50);
        tvRemote.turnDeviceOff();

        System.out.println();

        Device radio = new Radio();
        AdvancedRemoteControl radioRemote = new AdvancedRemoteControl(radio);
        radioRemote.turnDeviceOn();
        radioRemote.setDeviceVolume(70);
        radioRemote.mute();
        radioRemote.turnDeviceOff();
    }
}
