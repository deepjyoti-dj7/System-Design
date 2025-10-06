package BookMyShow.BookMyShow.service;

import BookMyShow.BookMyShow.dto.SeatDto;
import BookMyShow.BookMyShow.entity.Seat;
import BookMyShow.BookMyShow.entity.Screen;
import BookMyShow.BookMyShow.repository.SeatRepository;
import BookMyShow.BookMyShow.repository.ScreenRepository;
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
public class SeatService {

    private static final Logger logger = LoggerFactory.getLogger(SeatService.class);

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    // ==================== FETCH SEATS ====================

    public List<SeatDto.SeatResponse> getSeatsByScreen(Long screenId) {
        logger.info("Fetching seats for screenId: {}", screenId);
        return seatRepository.findByScreenId(screenId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Optional<SeatDto.SeatResponse> getSeat(Long id) {
        logger.info("Fetching seat by id: {}", id);
        return seatRepository.findById(id)
                .map(this::toResponse);
    }

    // ==================== CREATE SEAT ====================

    public SeatDto.SeatResponse addSeat(@Valid SeatDto.SeatRequest request) {
        Seat seat = toEntity(request);
        Seat saved = seatRepository.save(seat);
        logger.info("Seat created with id: {} for screen {}", saved.getId(), saved.getScreen().getId());
        return toResponse(saved);
    }

    public List<SeatDto.SeatResponse> addSeatsBulk(List<SeatDto.SeatRequest> requests) {
        logger.info("Adding {} seats in bulk", requests.size());

        List<Seat> seats = requests.stream()
                .map(this::toEntity)
                .toList();

        List<Seat> savedSeats = seatRepository.saveAll(seats);

        logger.info("Bulk inserted {} seats", savedSeats.size());
        return savedSeats.stream()
                .map(this::toResponse)
                .toList();
    }

    // ==================== UPDATE SEAT ====================

    public Optional<SeatDto.SeatResponse> updateSeat(Long id, @Valid SeatDto.SeatRequest request) {
        return seatRepository.findById(id).map(seat -> {
            if (request.getSeatNumber() != null) seat.setSeatNumber(request.getSeatNumber());
            if (request.getType() != null) seat.setType(request.getType());

            Seat updated = seatRepository.save(seat);
            logger.info("Seat updated with id: {}", updated.getId());
            return toResponse(updated);
        });
    }

    // ==================== DELETE SEAT ====================

    public boolean deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            logger.warn("Seat not found with id: {}", id);
            return false;
        }
        seatRepository.deleteById(id);
        logger.info("Seat deleted with id: {}", id);
        return true;
    }

    // ==================== HELPERS ====================

    private SeatDto.SeatResponse toResponse(Seat seat) {
        return new SeatDto.SeatResponse(
                seat.getId(),
                seat.getScreen().getId(),
                seat.getScreen().getName(),
                seat.getSeatNumber(),
                seat.getType(),
                seat.getIsBooked()
        );
    }

    private Seat toEntity(SeatDto.SeatRequest request) {
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("Screen not found with id: " + request.getScreenId()));

        return Seat.builder()
                .screen(screen)
                .seatNumber(request.getSeatNumber())
                .type(request.getType())
                .isBooked(false)
                .build();
    }
}
