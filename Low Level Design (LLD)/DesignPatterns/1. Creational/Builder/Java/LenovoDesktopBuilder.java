public class LenovoDesktopBuilder extends DesktopBuilder {

    @Override
    public void buildMonitor() {
        desktop.setMonitor("Lenovo 1440p 27-inch 144hz IPS");
    }

    @Override
    public void buildMouse() {
        desktop.setMouse("Lenovo Ergonomic Mouse");
    }

    @Override
    public void buildKeyboard() {
        desktop.setKeyboard("Lenovo Mechanical Keyboard");
    }

    @Override
    public void buildSpeaker() {
        desktop.setSpeaker("Dolby Audio Speakers");
    }

    @Override
    public void buildRam() {
        desktop.setRam("24GB DDR5");
    }

    @Override
    public void buildProcessor() {
        desktop.setProcessor("Intel i7 13650hx");
    }

    @Override
    public void buildMotherboard() {
        desktop.setMotherboard("Lenovo Legion 5");
    }

    @Override
    public void buildGraphicsCard() {
        desktop.setGraphicsCard("Nvidia RTX 4060");
    }
}
