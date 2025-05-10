public class AsusDesktopBuilder extends DesktopBuilder {

    @Override
    public void buildMonitor() {
        desktop.setMonitor("Asus 24-inch LED");
    }

    @Override
    public void buildMouse() {
        desktop.setMouse("Asus Gaming Mouse");
    }

    @Override
    public void buildKeyboard() {
        desktop.setKeyboard("Asus Mechanical Keyboard");
    }

    @Override
    public void buildSpeaker() {
        desktop.setSpeaker("Asus SonicMaster");
    }

    @Override
    public void buildRam() {
        desktop.setRam("16GB DDR4");
    }

    @Override
    public void buildProcessor() {
        desktop.setProcessor("Intel Core i7");
    }

    @Override
    public void buildMotherboard() {
        desktop.setMotherboard("Asus ROG Strix Z690");
    }
}
