package BookMyShow.BookMyShow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MovieDto {

    private MovieDto() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieRequest {
        @NotBlank(message = "Title is required")
        private String title;

        private String runtime;
        private String language;
        private String description;
        private String genre;
        private String certification;
        private LocalDate releaseDate;
        private String posterUrl;
        private String trailerUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieResponse {
        private Long id;
        private String title;
        private String runtime;
        private String language;
        private String description;
        private String genre;
        private String certification;
        private LocalDate releaseDate;
        private String posterUrl;
        private String trailerUrl;
    }
}
