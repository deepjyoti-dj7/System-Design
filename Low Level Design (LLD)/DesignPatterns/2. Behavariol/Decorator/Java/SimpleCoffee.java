public class SimpleCoffee implements Coffee {

    private double cost = 5.0;

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return cost;
    }
}
