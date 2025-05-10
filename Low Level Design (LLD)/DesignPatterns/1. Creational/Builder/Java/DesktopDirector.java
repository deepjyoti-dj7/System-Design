public class DesktopDirector {
    private DesktopBuilder builder;

    public DesktopDirector(DesktopBuilder builder) {
        this.builder = builder;
    }

    public Desktop buildDesktop() {
        builder.buildMonitor();
        builder.buildMouse();
        builder.buildKeyboard();
        builder.buildSpeaker();
        builder.buildRam();
        builder.buildProcessor();
        builder.buildMotherboard();
        builder.buildGraphicsCard();
        return builder.getDesktop();
    }
}
