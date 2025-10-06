package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScreenDto {

    private ScreenDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScreenRequest {
        @NotNull(message = "Theatre ID is required")
        private Long theatreId;

        @NotBlank(message = "Screen name is required")
        private String name;

        @NotNull(message = "Total seats is required")
        private Integer totalSeats;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScreenResponse {
        private Long id;
        private Long theatreId;
        private String theatreName;
        private String name;
        private Integer totalSeats;
    }
}
