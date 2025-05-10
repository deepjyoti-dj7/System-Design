public class Main {
    public static void main(String[] args) {
        DesktopBuilder lenovoBuilder = new LenovoDesktopBuilder();
        DesktopBuilder asusBuilder = new AsusDesktopBuilder();

        DesktopDirector director1 = new DesktopDirector(lenovoBuilder);
        DesktopDirector director2 = new DesktopDirector(asusBuilder);

        Desktop lenovoDesktop  = director1.buildDesktop();
        Desktop asusDesktop  = director2.buildDesktop();

        lenovoDesktop .showSpecifications();
        asusDesktop .showSpecifications();
    }
}
