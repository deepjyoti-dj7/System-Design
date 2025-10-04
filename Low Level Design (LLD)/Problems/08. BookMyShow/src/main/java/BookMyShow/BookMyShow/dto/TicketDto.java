package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TicketDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketRequest {
        @NotNull(message = "Booking ID is required")
        private Long bookingId;

        private String seatNumber;
        private Double price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketResponse {
        private Long id;
        private Long bookingId;
        private String seatNumber;
        private Double price;
    }
}
