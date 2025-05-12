public class SugarDecorator extends CoffeeDecorator {

    public double sugarCost = 0.5;

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + sugarCost;
    }
}
