package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class SeatDto {

    private SeatDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatRequest {
        @NotNull(message = "Screen ID is required")
        private Long screenId;

        @NotBlank(message = "Seat number is required")
        private String seatNumber;

        @NotBlank(message = "Seat type is required")
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatResponse {
        private Long id;
        private Long screenId;
        private String screenName;
        private String seatNumber;
        private String type;
        private Boolean isBooked;
    }
}

