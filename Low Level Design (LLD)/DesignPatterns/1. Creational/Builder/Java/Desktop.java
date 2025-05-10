public class Desktop {
    private String monitor;
    private String mouse;
    private String keyboard;
    private String speaker;
    private String ram;
    private String processor;
    private String motherboard;

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public void showSpecifications() {
        System.out.println("Desktop Specifications:");
        System.out.println("- Monitor: " + monitor);
        System.out.println("- Mouse: " + mouse);
        System.out.println("- Keyboard: " + keyboard);
        System.out.println("- Speaker: " + speaker);
        System.out.println("- RAM: " + ram);
        System.out.println("- Processor: " + processor);
        System.out.println("- Motherboard: " + motherboard);
        System.out.println("-----------------------------------");
    }
}
