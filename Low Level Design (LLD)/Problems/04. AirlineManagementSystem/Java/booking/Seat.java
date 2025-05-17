package booking;

import utils.SeatClass;
import utils.SeatStatus;

public class Seat {
    private String seatNumber;
    private SeatClass seatClass;
    private SeatStatus seatStatus;

    public Seat(String seatNumber, SeatClass seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
    }

    public void book() {
        this.seatStatus = SeatStatus.BOOKED;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }
}
