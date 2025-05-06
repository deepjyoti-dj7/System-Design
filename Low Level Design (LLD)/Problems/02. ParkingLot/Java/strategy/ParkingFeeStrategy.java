package strategy;

import java.time.LocalDateTime;
import model.Vehicle;

public interface ParkingFeeStrategy {
    double calculateFee(Vehicle vehicle, LocalDateTime entry, LocalDateTime exit);
}

