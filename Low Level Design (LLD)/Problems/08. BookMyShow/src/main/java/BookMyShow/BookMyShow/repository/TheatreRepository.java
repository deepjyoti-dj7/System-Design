package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    List<Theatre> findByCity(String city);
    List<Theatre> findByName(String name);
}
