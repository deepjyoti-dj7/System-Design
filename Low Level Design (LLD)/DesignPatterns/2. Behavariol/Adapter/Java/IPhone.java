public class IPhone {
    private LightningPort port;

    public IPhone(LightningPort port) {
        this.port = port;
    }

    public void charge() {
        port.connectWithLightning();
        System.out.println("Phone is charging...");
    }
}
