public class Main {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();

        PhoneDisplay phoneDisplay = new PhoneDisplay(station);
        LaptopDisplay laptopDisplay = new LaptopDisplay(station);

        station.setTemperature(25.0f);
        station.setTemperature(30.5f);
    }
}
