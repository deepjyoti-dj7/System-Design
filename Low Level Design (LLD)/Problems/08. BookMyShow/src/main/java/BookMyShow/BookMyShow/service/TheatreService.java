package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.MovieDto;
import BookMyShow.BookMyShow.dto.TheatreDto;
import BookMyShow.BookMyShow.entity.Movie;
import BookMyShow.BookMyShow.entity.Theatre;
import BookMyShow.BookMyShow.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TheatreService {

    private static final Logger logger = LoggerFactory.getLogger(TheatreService.class);
    private final TheatreRepository theatreRepository;

    // ==================== FETCH THEATRES ====================

    public List<TheatreDto.TheatreResponse> getAllTheatres() {
        logger.info("Fetching all theatres");
        return theatreRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<TheatreDto.TheatreResponse> getTheatre(Long id) {
        logger.info("Fetching theatre by id: {}", id);
        return theatreRepository.findById(id)
                .map(this::toResponse);
    }

    public List<TheatreDto.TheatreResponse> getTheatresByCity(String city) {
        logger.info("Fetching theatre by city: {}", city);
        return theatreRepository.findByCity(city)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TheatreDto.TheatreResponse> searchTheatres(String name) {
        logger.info("Searching theatres with name containing: {}", name);
        return theatreRepository.findByName(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==================== CREATE THEATRE ====================

    public TheatreDto.TheatreResponse addTheatre(TheatreDto.TheatreRequest request) {
        Theatre theatre = toEntity(request);
        Theatre saved = theatreRepository.save(theatre);
        logger.info("Theatre created with id: {}", saved.getId());
        return toResponse(saved);
    }

    // ==================== UPDATE THEATRE ====================

    public Optional<TheatreDto.TheatreResponse> updateTheatre(Long id, TheatreDto.TheatreRequest request) {
        return theatreRepository.findById(id).map(theatre -> {
            if (request.getName() != null) theatre.setName(request.getName());
            if (request.getCity() != null) theatre.setCity(request.getCity());
            if (request.getAddress() != null) theatre.setAddress(request.getAddress());

            Theatre updated = theatreRepository.save(theatre);
            logger.info("Theatre updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }
    // ==================== DELETE THEATRE ====================

    public boolean deleteTheatre(Long id) {
        if (!theatreRepository.existsById(id)) {
            logger.warn("Theatre not found with id {}", id);
            return false;
        }
        theatreRepository.deleteById(id);
        logger.info("Theatre deleted with id: {}", id);
        return true;
    }

    private TheatreDto.TheatreResponse toResponse(Theatre theatre) {
        return new TheatreDto.TheatreResponse(
                theatre.getId(),
                theatre.getName(),
                theatre.getCity(),
                theatre.getAddress()
        );
    }

    private Theatre toEntity(TheatreDto.TheatreRequest request) {
        return Theatre.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();
    }
}
