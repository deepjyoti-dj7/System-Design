public class Circle implements Shape {
    private String color; // Intrinsic state (shared)

    // Extrinsic state will be passed via draw method
    public Circle(String color) {
        this.color = color;
        System.out.println("Creating circle of color: " + color);
    }

    @Override
    public void draw(int x, int y, int radius) {
        System.out.println("Circle drawn [Color: " + color + ", X: " + x + ", Y: " + y + ", Radius: " + radius + "]\n");
    }
}
