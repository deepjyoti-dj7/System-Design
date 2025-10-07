package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class SeatLockDto {

    private SeatLockDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatLockRequest {
        @NotNull(message = "Seat ID is required")
        private Long seatId;

        @NotNull(message = "Show ID is required")
        private Long showId;

        private String sessionId;
        private LocalDateTime lockedAt;
        private LocalDateTime expiresAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatLockResponse {
        private Long id;
        private Long seatId;
        private Long showId;
        private String sessionId;
        private LocalDateTime lockedAt;
        private LocalDateTime expiresAt;
    }
}
