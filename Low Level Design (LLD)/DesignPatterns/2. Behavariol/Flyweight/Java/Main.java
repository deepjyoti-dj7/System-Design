public class Main {
    private static final String[] colors = { "Red", "Green", "Blue", "White", "Black" };

    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            String color = getRandomColor();
            Shape circle = ShapeFactory.getCircle(color);
            circle.draw(getRandomX(), getRandomY(), 100);
        }
    }

    private static String getRandomColor() {
        return colors[(int)(Math.random() * colors.length)];
    }

    private static int getRandomX() {
        return (int)(Math.random() * 100);
    }

    private static int getRandomY() {
        return (int)(Math.random() * 100);
    }
}
