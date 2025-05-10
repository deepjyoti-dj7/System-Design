public class TShirt implements Product {
    private String size;
    private String color;

    public TShirt(String size, String color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public Product clone() {
        return new TShirt(this.size, this.color);
    }

    @Override
    public void showDetails() {
        System.out.println("T-Shirt [Size: " + size + ", Color: " + color + "]");
    }

    // Optional: Setters for customization
    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
