package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByStatus(String status);
}
