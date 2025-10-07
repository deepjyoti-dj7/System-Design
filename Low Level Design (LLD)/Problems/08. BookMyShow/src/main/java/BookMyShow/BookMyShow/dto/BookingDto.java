package BookMyShow.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class BookingDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingRequest {
        private Long bookingId;
        private Long userId;
        private Long showId;
        private List<String> seatNumbers;
        private String paymentToken;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingResponse {
        private Long bookingId;
        private String status;
        private List<String> seatsBooked;
        private double totalPrice;
    }
}
