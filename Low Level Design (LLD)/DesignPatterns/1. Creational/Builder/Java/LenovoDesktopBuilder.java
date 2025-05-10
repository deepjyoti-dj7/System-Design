public class LenovoDesktopBuilder extends DesktopBuilder {

    @Override
    public void buildMonitor() {
        desktop.setMonitor("Lenovo 27-inch IPS");
    }

    @Override
    public void buildMouse() {
        desktop.setMouse("Lenovo Ergonomic Mouse");
    }

    @Override
    public void buildKeyboard() {
        desktop.setKeyboard("Lenovo ThinkPad Keyboard");
    }

    @Override
    public void buildSpeaker() {
        desktop.setSpeaker("Dolby Audio Speakers");
    }

    @Override
    public void buildRam() {
        desktop.setRam("32GB DDR5");
    }

    @Override
    public void buildProcessor() {
        desktop.setProcessor("AMD Ryzen 9");
    }

    @Override
    public void buildMotherboard() {
        desktop.setMotherboard("Lenovo Legion Pro");
    }
}
