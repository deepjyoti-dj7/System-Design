public class Main {
    public static void main(String[] args) {
        // Base product
        TShirt baseTShirt = new TShirt("M", "Black");

        // Clone the product
        TShirt customer1Shirt = (TShirt) baseTShirt.clone();
        TShirt customer2Shirt = (TShirt) baseTShirt.clone();

        // Customize clones
        customer1Shirt.setColor("Red");
        customer2Shirt.setSize("L");

        // Show details
        baseTShirt.showDetails();
        customer1Shirt.showDetails();
        customer2Shirt.showDetails();
    }
}
