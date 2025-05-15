public class PhoneDisplay implements Observer {
    private Subject weatherStation;

    public PhoneDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.addObserver(this);
    }

    @Override
    public void update(float temperature) {
        System.out.println("Phone Display: Temperature updated to " + temperature + "°C");
    }
}
