package BookMyShow.BookMyShow.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TheatreDto {

    private TheatreDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheatreRequest {
        @NotBlank(message = "Name is required")
        private String name;
        private String city;
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TheatreResponse {
        private Long id;
        private String name;
        private String city;
        private String address;
    }
}
