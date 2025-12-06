# Urban Company Clone - Test Suite

Comprehensive test suite for all microservices using JUnit 5, Mockito, Spring Boot Test, and TestContainers.

## Test Coverage

### 1. User Service Tests
- **UserServiceTest.java** (12 tests)
  - User registration with validation
  - Email verification
  - Profile updates
  - Password change
  - User retrieval

- **UserControllerTest.java** (7 tests)
  - REST API endpoints
  - Authentication
  - Input validation
  - Error handling

- **UserRepositoryTest.java** (8 tests)
  - Database operations
  - Query methods
  - Data persistence

- **JwtTokenProviderTest.java** (5 tests)
  - Token generation
  - Token validation
  - Expiration handling

### 2. Booking Service Tests
- **BookingServiceTest.java** (12 tests)
  - Booking creation
  - Status transitions
  - Partner assignment
  - Booking cancellation

- **BookingStateMachineTest.java** (6 tests)
  - State machine transitions
  - Invalid state changes
  - State validation

- **PricingServiceTest.java** (4 tests)
  - Base price calculation
  - Surcharge application (weekend, peak hours)
  - Dynamic pricing

- **BookingControllerTest.java** (7 tests)
  - Booking API endpoints
  - Request validation
  - Response formatting

### 3. Payment Service Tests
- **PaymentServiceTest.java** (10 tests)
  - Payment processing
  - Refund handling
  - Gateway selection
  - Payment status

- **StripePaymentStrategyTest.java** (6 tests)
  - Stripe integration
  - Card processing
  - Error handling

- **RazorpayPaymentStrategyTest.java** (5 tests)
  - Razorpay integration
  - UPI payments
  - Currency conversion

- **PaymentControllerTest.java** (5 tests)
  - Payment API endpoints
  - Transaction validation

### 4. Partner Service Tests
- **PartnerServiceTest.java** (11 tests)
  - Partner registration
  - Verification workflow
  - Geospatial queries (nearby partners)
  - Rating updates
  - Status management

- **PartnerControllerTest.java** (7 tests)
  - Partner API endpoints
  - Location-based search
  - Admin operations

- **PartnerRepositoryTest.java** (10 tests)
  - Database operations
  - Geospatial queries
  - Custom query methods

### 5. Catalog Service Tests
- **CatalogServiceTest.java** (11 tests)
  - Service CRUD operations
  - Category filtering
  - Search functionality
  - Rating updates
  - Popular services

- **CategoryServiceTest.java** (11 tests)
  - Category management
  - Hierarchical categories
  - Display order
  - Subcategory handling

- **CatalogControllerTest.java** (8 tests)
  - Catalog API endpoints
  - Search endpoints
  - Admin operations

### 6. Integration Tests
- **BookingFlowIntegrationTest.java** (10 tests)
  - End-to-end booking flow
  - User registration → Service selection → Partner assignment → Payment → Completion
  - Rating and review
  - Uses TestContainers (PostgreSQL, RabbitMQ)

- **PaymentGatewayIntegrationTest.java** (7 tests)
  - Multiple payment gateway testing
  - Refund processing
  - Concurrent payment handling
  - Payment validation

## Test Technologies

### Core Testing Frameworks
- **JUnit 5**: Modern testing framework with annotations
- **Mockito**: Mocking framework for unit tests
- **AssertJ**: Fluent assertion library
- **Spring Boot Test**: Spring context testing support

### Testing Strategies
- **@ExtendWith(MockitoExtension.class)**: Unit test isolation
- **@WebMvcTest**: Controller layer testing with MockMvc
- **@DataJpaTest**: Repository layer testing with in-memory H2
- **@SpringBootTest**: Full application context for integration tests

### TestContainers
- **PostgreSQL Container**: Real database for integration tests
- **RabbitMQ Container**: Message queue testing
- Automatic container lifecycle management

## Running Tests

### Run All Tests
```bash
# From root directory
./mvnw clean test

# Run specific service tests
cd services/user-service
./mvnw test

cd services/booking-service
./mvnw test
```

### Run Integration Tests
```bash
cd services/integration-tests
./mvnw verify
```

### Run Tests with Coverage
```bash
# Add JaCoCo plugin to pom.xml, then:
./mvnw clean test jacoco:report
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=UserServiceTest
./mvnw test -Dtest=BookingFlowIntegrationTest
```

### Run Specific Test Method
```bash
./mvnw test -Dtest=UserServiceTest#shouldRegisterUserSuccessfully
```

## Test Configuration

### Test Properties
Each service has `application-test.yml` with:
- H2 in-memory database
- Disabled external dependencies
- Test-specific configurations
- Debug logging

### Mock Data
- Test fixtures in `@BeforeEach` methods
- Builder pattern for test objects
- Consistent test data across tests

## Test Patterns

### Arrange-Act-Assert (AAA)
```java
@Test
void shouldCreateBooking() {
    // Given (Arrange)
    when(repository.save(any())).thenReturn(booking);
    
    // When (Act)
    BookingDto result = service.createBooking(request);
    
    // Then (Assert)
    assertThat(result).isNotNull();
    verify(repository).save(any());
}
```

### Given-When-Then (BDD)
```java
@Test
@DisplayName("Should process payment successfully")
void shouldProcessPayment() {
    // Given
    PaymentRequest request = createPaymentRequest();
    
    // When
    PaymentDto result = paymentService.processPayment(request);
    
    // Then
    assertThat(result.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
}
```

### MockMvc Testing
```java
@Test
@WithMockUser
void shouldGetUserById() throws Exception {
    mockMvc.perform(get("/api/v1/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
}
```

## Code Coverage Goals
- **Unit Tests**: 80%+ line coverage
- **Integration Tests**: Critical user flows
- **Controller Tests**: All REST endpoints
- **Repository Tests**: Custom query methods

## CI/CD Integration
Tests are designed to run in CI/CD pipelines:
- Fast unit tests (< 30 seconds per service)
- Isolated integration tests with containers
- No external dependencies required
- Parallel test execution support

## Best Practices

### ✅ DO
- Use descriptive test names with `@DisplayName`
- Follow AAA/Given-When-Then pattern
- Mock external dependencies
- Test both happy and error paths
- Use `ArgumentCaptor` to verify mock interactions
- Clean up test data in `@BeforeEach`

### ❌ DON'T
- Don't test framework code (Spring, JPA)
- Don't use real external services in unit tests
- Don't share mutable state between tests
- Don't write flaky tests with timing dependencies
- Don't ignore test failures

## Troubleshooting

### Tests Fail with "Database locked"
- Ensure H2 in-memory mode is used
- Check `application-test.yml` has correct URL

### TestContainers fail to start
- Ensure Docker is running
- Check Docker daemon is accessible
- Increase Docker memory limits

### Tests are slow
- Use `@WebMvcTest` instead of `@SpringBootTest` for controllers
- Use `@DataJpaTest` instead of full context for repositories
- Mock external services
- Use test slicing

## Adding New Tests

### 1. Create Test Class
```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Feature Service Tests")
class FeatureServiceTest {
    
    @Mock
    private Repository repository;
    
    @InjectMocks
    private FeatureService service;
    
    @BeforeEach
    void setUp() {
        // Initialize test data
    }
    
    @Test
    @DisplayName("Should do something")
    void shouldDoSomething() {
        // Test implementation
    }
}
```

### 2. Controller Test
```java
@WebMvcTest(FeatureController.class)
class FeatureControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FeatureService service;
    
    @Test
    @WithMockUser
    void shouldCallEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/features"))
            .andExpect(status().isOk());
    }
}
```

### 3. Repository Test
```java
@DataJpaTest
class FeatureRepositoryTest {
    
    @Autowired
    private FeatureRepository repository;
    
    @Test
    void shouldFindByCustomQuery() {
        // Test custom query methods
    }
}
```

## Test Statistics

### Total Test Count: 140+ tests
- User Service: 32 tests
- Booking Service: 29 tests
- Payment Service: 26 tests
- Partner Service: 28 tests
- Catalog Service: 30 tests
- Integration Tests: 17 tests

### Execution Time
- Unit Tests: ~2-3 minutes total
- Integration Tests: ~5-7 minutes (with containers)
- Full Suite: ~10 minutes

## Future Enhancements

1. **Performance Tests**: Add JMeter/Gatling tests
2. **Security Tests**: Add penetration testing
3. **Load Tests**: Add stress testing scenarios
4. **Contract Tests**: Add Pact for API contracts
5. **Mutation Testing**: Add PIT mutation testing
6. **Code Coverage**: Add JaCoCo for coverage reports
7. **Test Data Builders**: Add test data factories
8. **Cucumber Tests**: Add BDD scenarios

## Resources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [TestContainers](https://www.testcontainers.org/)
- [AssertJ](https://assertj.github.io/doc/)
