package booking;

import utils.SeatClass;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class SeatFactory {
    public static List<Seat> generateSeats(Map<SeatClass, Integer> seatConfig) {
        List<Seat> seats = new ArrayList<>();
        int counter = 1;
        for (Map.Entry<SeatClass, Integer> entry : seatConfig.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                seats.add(new Seat(entry.getKey().name().charAt(0) + String.valueOf(counter++), entry.getKey()));
            }
        }
        return seats;
    }
}
