package BookMyShow.BookMyShow.repository;

import BookMyShow.BookMyShow.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserRepository() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        userRepository.save(user);

        List<User> users = userRepository.findAll();
        assert(!users.isEmpty());
        System.out.println(users);
    }
}

