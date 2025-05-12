public class USBCToLightningAdapter implements LightningPort {
    private USBCCharger usbcCharger;

    public USBCToLightningAdapter(USBCCharger usbcCharger) {
        this.usbcCharger = usbcCharger;
    }

    @Override
    public void connectWithLightning() {
        System.out.println("Using USB-C to Lightning adapter...");
        usbcCharger.connectWithUSBC();
    }
}
