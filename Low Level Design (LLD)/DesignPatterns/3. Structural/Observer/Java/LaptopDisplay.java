public class LaptopDisplay implements Observer {
    private Subject weatherStation;

    public LaptopDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.addObserver(this);
    }

    @Override
    public void update(float temperature) {
        System.out.println("Laptop Display: Temperature updated to " + temperature + "°C");
    }
}
