public class Main {
    public static void main(String[] args) {
        USBCCharger usbcCharger = new USBCCharger();
        LightningPort adapter = new USBCToLightningAdapter(usbcCharger);

        IPhone iPhone = new IPhone(adapter);
        iPhone.charge();
    }
}
