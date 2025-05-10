public abstract class DesktopBuilder {
    protected Desktop desktop = new Desktop();

    public Desktop getDesktop() {
        return desktop;
    }

    public abstract void buildMonitor();
    public abstract void buildMouse();
    public abstract void buildKeyboard();
    public abstract void buildSpeaker();
    public abstract void buildRam();
    public abstract void buildProcessor();
    public abstract void buildMotherboard();
    public abstract void buildGraphicsCard();
}
