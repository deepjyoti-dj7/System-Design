package BookMyShow.BookMyShow;

import BookMyShow.BookMyShow.entity.User;
import BookMyShow.BookMyShow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	// ===================== Create First Admin =====================
	@Bean
	public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = User.builder()
						.username("admin")
						.email("admin@example.com")
						.name("Admin User")
						.phone("0000000000")
						.password(passwordEncoder.encode("Admin@123"))  // secure password
						.roles(Set.of("ADMIN"))  // just "ADMIN", JWT filter will handle ROLE_ prefix
						.build();
				userRepository.save(admin);
				System.out.println("Admin user created!");
			}
		};
	}
}
