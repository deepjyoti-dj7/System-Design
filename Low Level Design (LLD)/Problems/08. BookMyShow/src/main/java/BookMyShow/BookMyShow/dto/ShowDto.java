package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ShowDto {

    private ShowDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowRequest {
        @NotNull(message = "Movie ID is required")
        private Long movieId;

        @NotNull(message = "Screen ID is required")
        private Long screenId;

        @NotNull(message = "Start time is required")
        private LocalDateTime startTime;

        @NotNull(message = "End time is required")
        private LocalDateTime endTime;

        @NotNull(message = "Base price is required")
        private Double basePrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowResponse {
        private Long id;
        private Long movieId;
        private String movieName;
        private Long screenId;
        private String screenName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double basePrice;
    }
}
