package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
