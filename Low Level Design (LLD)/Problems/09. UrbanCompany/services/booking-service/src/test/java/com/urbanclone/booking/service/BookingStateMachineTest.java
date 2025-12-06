package com.urbanclone.booking.service;

import com.urbanclone.booking.entity.BookingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Booking State Machine Tests")
class BookingStateMachineTest {

    private BookingStateMachine stateMachine;

    @BeforeEach
    void setUp() {
        stateMachine = new BookingStateMachine();
    }

    @Test
    @DisplayName("Should allow transition from PENDING to CONFIRMED")
    void shouldAllowPendingToConfirmed() {
        // When
        boolean canTransition = stateMachine.canTransition(BookingStatus.PENDING, BookingStatus.CONFIRMED);

        // Then
        assertThat(canTransition).isTrue();
    }

    @Test
    @DisplayName("Should allow transition from CONFIRMED to IN_PROGRESS")
    void shouldAllowConfirmedToInProgress() {
        // When
        boolean canTransition = stateMachine.canTransition(BookingStatus.CONFIRMED, BookingStatus.IN_PROGRESS);

        // Then
        assertThat(canTransition).isTrue();
    }

    @Test
    @DisplayName("Should allow transition from IN_PROGRESS to COMPLETED")
    void shouldAllowInProgressToCompleted() {
        // When
        boolean canTransition = stateMachine.canTransition(BookingStatus.IN_PROGRESS, BookingStatus.COMPLETED);

        // Then
        assertThat(canTransition).isTrue();
    }

    @Test
    @DisplayName("Should allow transition to CANCELLED from any state except COMPLETED")
    void shouldAllowCancellationFromMostStates() {
        // When & Then
        assertThat(stateMachine.canTransition(BookingStatus.PENDING, BookingStatus.CANCELLED)).isTrue();
        assertThat(stateMachine.canTransition(BookingStatus.CONFIRMED, BookingStatus.CANCELLED)).isTrue();
        assertThat(stateMachine.canTransition(BookingStatus.IN_PROGRESS, BookingStatus.CANCELLED)).isTrue();
        assertThat(stateMachine.canTransition(BookingStatus.COMPLETED, BookingStatus.CANCELLED)).isFalse();
    }

    @Test
    @DisplayName("Should not allow invalid transitions")
    void shouldNotAllowInvalidTransitions() {
        // When & Then
        assertThat(stateMachine.canTransition(BookingStatus.PENDING, BookingStatus.IN_PROGRESS)).isFalse();
        assertThat(stateMachine.canTransition(BookingStatus.PENDING, BookingStatus.COMPLETED)).isFalse();
        assertThat(stateMachine.canTransition(BookingStatus.COMPLETED, BookingStatus.PENDING)).isFalse();
        assertThat(stateMachine.canTransition(BookingStatus.COMPLETED, BookingStatus.CONFIRMED)).isFalse();
    }

    @Test
    @DisplayName("Should not allow backward transitions")
    void shouldNotAllowBackwardTransitions() {
        // When & Then
        assertThat(stateMachine.canTransition(BookingStatus.CONFIRMED, BookingStatus.PENDING)).isFalse();
        assertThat(stateMachine.canTransition(BookingStatus.IN_PROGRESS, BookingStatus.CONFIRMED)).isFalse();
        assertThat(stateMachine.canTransition(BookingStatus.COMPLETED, BookingStatus.IN_PROGRESS)).isFalse();
    }
}
