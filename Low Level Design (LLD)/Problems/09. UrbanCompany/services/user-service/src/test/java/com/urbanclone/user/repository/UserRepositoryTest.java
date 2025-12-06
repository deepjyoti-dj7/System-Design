package com.urbanclone.user.repository;

import com.urbanclone.user.entity.User;
import com.urbanclone.user.entity.UserRole;
import com.urbanclone.user.entity.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("User Repository Tests")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .password("encodedPassword")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUser() {
        // When
        User savedUser = userRepository.save(testUser);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(savedUser.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        // Given
        entityManager.persist(testUser);
        entityManager.flush();

        // When
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void shouldReturnEmptyWhenEmailNotFound() {
        // When
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find user by phone number")
    void shouldFindUserByPhoneNumber() {
        // Given
        entityManager.persist(testUser);
        entityManager.flush();

        // When
        Optional<User> found = userRepository.findByPhoneNumber("+1234567890");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getPhoneNumber()).isEqualTo(testUser.getPhoneNumber());
    }

    @Test
    @DisplayName("Should check if email exists")
    void shouldCheckIfEmailExists() {
        // Given
        entityManager.persist(testUser);
        entityManager.flush();

        // When
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should check if phone number exists")
    void shouldCheckIfPhoneNumberExists() {
        // Given
        entityManager.persist(testUser);
        entityManager.flush();

        // When
        boolean exists = userRepository.existsByPhoneNumber("+1234567890");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should find users by role")
    void shouldFindUsersByRole() {
        // Given
        entityManager.persist(testUser);
        
        User partnerUser = User.builder()
                .email("partner@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .phoneNumber("+9876543210")
                .password("encodedPassword")
                .role(UserRole.PARTNER)
                .status(UserStatus.ACTIVE)
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        entityManager.persist(partnerUser);
        entityManager.flush();

        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> customers = userRepository.findByRole(UserRole.CUSTOMER, pageable);
        Page<User> partners = userRepository.findByRole(UserRole.PARTNER, pageable);

        // Then
        assertThat(customers.getContent()).hasSize(1);
        assertThat(customers.getContent().get(0).getRole()).isEqualTo(UserRole.CUSTOMER);
        assertThat(partners.getContent()).hasSize(1);
        assertThat(partners.getContent().get(0).getRole()).isEqualTo(UserRole.PARTNER);
    }

    @Test
    @DisplayName("Should find users by status")
    void shouldFindUsersByStatus() {
        // Given
        entityManager.persist(testUser);
        
        User inactiveUser = User.builder()
                .email("inactive@example.com")
                .firstName("Inactive")
                .lastName("User")
                .phoneNumber("+1111111111")
                .password("encodedPassword")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.INACTIVE)
                .emailVerified(false)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        entityManager.persist(inactiveUser);
        entityManager.flush();

        // When
        List<User> activeUsers = userRepository.findByStatus(UserStatus.ACTIVE);
        List<User> inactiveUsers = userRepository.findByStatus(UserStatus.INACTIVE);

        // Then
        assertThat(activeUsers).hasSize(1);
        assertThat(inactiveUsers).hasSize(1);
    }

    @Test
    @DisplayName("Should find users by email verified status")
    void shouldFindUsersByEmailVerified() {
        // Given
        entityManager.persist(testUser);
        
        User verifiedUser = User.builder()
                .email("verified@example.com")
                .firstName("Verified")
                .lastName("User")
                .phoneNumber("+2222222222")
                .password("encodedPassword")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .emailVerified(true)
                .phoneVerified(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        entityManager.persist(verifiedUser);
        entityManager.flush();

        // When
        List<User> unverifiedUsers = userRepository.findByEmailVerified(false);
        List<User> verifiedUsers = userRepository.findByEmailVerified(true);

        // Then
        assertThat(unverifiedUsers).hasSize(1);
        assertThat(verifiedUsers).hasSize(1);
    }
}
