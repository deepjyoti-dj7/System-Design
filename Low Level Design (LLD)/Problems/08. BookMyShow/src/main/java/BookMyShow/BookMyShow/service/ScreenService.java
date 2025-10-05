package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.ScreenDto;
import BookMyShow.BookMyShow.entity.Screen;
import BookMyShow.BookMyShow.entity.Theatre;
import BookMyShow.BookMyShow.repository.ScreenRepository;
import BookMyShow.BookMyShow.repository.TheatreRepository;
import jakarta.validation.Valid;
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
public class ScreenService {

    private static final Logger logger = LoggerFactory.getLogger(ScreenService.class);
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    // ==================== FETCH SCREENS ====================

    public List<ScreenDto.ScreenResponse> getAllScreens() {
        logger.info("Fetching all screens");
        return screenRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<ScreenDto.ScreenResponse> getScreen(Long id) {
        logger.info("Fetching screen by id: {}", id);
        return screenRepository.findById(id)
                .map(this::toResponse);
    }

    public List<ScreenDto.ScreenResponse> getScreensByTheatre(Long theatreId) {
        logger.info("Fetching screens for theatreId: {}", theatreId);
        return screenRepository.findByTheatreId(theatreId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ==================== CREATE SCREENS ====================

    public ScreenDto.ScreenResponse addScreen(@Valid ScreenDto.ScreenRequest request) {
        Screen screen = toEntity(request);
        Screen saved = screenRepository.save(screen);
        logger.info("Screen created with id: {}", saved.getId());
        return toResponse(saved);
    }

    // ==================== UPDATE SCREEN ====================

    public Optional<ScreenDto.ScreenResponse> updateScreen(Long id, @Valid ScreenDto.ScreenRequest request) {
        return screenRepository.findById(id).map(screen -> {
            if (request.getTheatreId() != null && theatreRepository.existsById(request.getTheatreId()))
                screen.setTheatre(theatreRepository.getReferenceById(request.getTheatreId()));
            if (request.getName() != null) screen.setName(request.getName());
            if (request.getTotalSeats() != null) screen.setTotalSeats(request.getTotalSeats());

            Screen updated = screenRepository.save(screen);
            logger.info("Screen updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    // ==================== DELETE SCREEN ====================

    public boolean deleteScreen(Long id) {
        if (!screenRepository.existsById(id)) {
            logger.warn("Screen not found with id: {}", id);
            return false;
        }
        screenRepository.deleteById(id);
        logger.info("Screen deleted with id: {}", id);
        return true;
    }

    private ScreenDto.ScreenResponse toResponse(Screen screen) {
        return new ScreenDto.ScreenResponse(
                screen.getId(),
                screen.getTheatre().getId(),
                screen.getName(),
                screen.getTotalSeats()
        );
    }

    private Screen toEntity(ScreenDto.ScreenRequest request) {
        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() -> new IllegalArgumentException("Theatre not found with id: " + request.getTheatreId()));

        return Screen.builder()
                .theatre(theatre)
                .name(request.getName())
                .totalSeats(request.getTotalSeats())
                .build();
    }
}
