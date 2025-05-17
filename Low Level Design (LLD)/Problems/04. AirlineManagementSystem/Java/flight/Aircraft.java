package flight;

import utils.SeatClass;

import java.util.Map;

public class Aircraft {
    private String model;
    private int capacity;
    private Map<SeatClass, Integer> seatConfig;

    public Aircraft(String model, int capacity, Map<SeatClass, Integer> seatConfig) {
        this.model = model;
        this.capacity = capacity;
        this.seatConfig = seatConfig;
    }

    public Map<SeatClass, Integer> getSeatConfig() {
        return seatConfig;
    }
}
