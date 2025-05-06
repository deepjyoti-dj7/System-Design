package strategy;

import model.VehicleType;
import model.Vehicle;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class VehicleBasedHourlyFeeStrategy implements ParkingFeeStrategy {
    private final Map<VehicleType, Double> hourlyRates = new HashMap<>();

    // Correct constructor
    public VehicleBasedHourlyFeeStrategy() {
        hourlyRates.put(VehicleType.BIKE, 20.0);
        hourlyRates.put(VehicleType.CAR, 40.0);
        hourlyRates.put(VehicleType.TRUCK, 70.0);
    }

    @Override
    public double calculateFee(Vehicle vehicle, LocalDateTime entry, LocalDateTime exit) {
        long hours = Math.max(1, Duration.between(entry, exit).toHours());

        VehicleType type = vehicle.getType();
        if (!hourlyRates.containsKey(type)) {
            throw new IllegalArgumentException("No hourly rate defined for vehicle type: " + type);
        }

        double rate = hourlyRates.get(type);
        return hours * rate;
    }
}
