public class MilkDecorator extends CoffeeDecorator {

    public double milkCost = 1.5;

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + milkCost;
    }
}
