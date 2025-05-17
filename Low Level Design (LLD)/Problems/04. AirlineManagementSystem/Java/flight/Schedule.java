package flight;

import java.time.LocalDateTime;

public class Schedule {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Schedule(LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
