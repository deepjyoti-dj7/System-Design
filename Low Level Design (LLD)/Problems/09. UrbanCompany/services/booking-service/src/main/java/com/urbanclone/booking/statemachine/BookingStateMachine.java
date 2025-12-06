package com.urbanclone.booking.statemachine;

import com.urbanclone.booking.entity.BookingStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class BookingStateMachine {
    
    private final Map<BookingStatus, Set<BookingStatus>> transitions;
    
    public BookingStateMachine() {
        transitions = new EnumMap<>(BookingStatus.class);
        initializeTransitions();
    }
    
    private void initializeTransitions() {
        // PENDING can transition to
        transitions.put(BookingStatus.PENDING, EnumSet.of(
                BookingStatus.PARTNER_ASSIGNED,
                BookingStatus.CANCELLED
        ));
        
        // PARTNER_ASSIGNED can transition to
        transitions.put(BookingStatus.PARTNER_ASSIGNED, EnumSet.of(
                BookingStatus.PARTNER_EN_ROUTE,
                BookingStatus.CANCELLED
        ));
        
        // PARTNER_EN_ROUTE can transition to
        transitions.put(BookingStatus.PARTNER_EN_ROUTE, EnumSet.of(
                BookingStatus.PARTNER_ARRIVED,
                BookingStatus.CANCELLED
        ));
        
        // PARTNER_ARRIVED can transition to
        transitions.put(BookingStatus.PARTNER_ARRIVED, EnumSet.of(
                BookingStatus.IN_PROGRESS,
                BookingStatus.CANCELLED
        ));
        
        // IN_PROGRESS can transition to
        transitions.put(BookingStatus.IN_PROGRESS, EnumSet.of(
                BookingStatus.COMPLETED
        ));
        
        // COMPLETED can transition to
        transitions.put(BookingStatus.COMPLETED, EnumSet.of(
                BookingStatus.PAYMENT_PENDING
        ));
        
        // PAYMENT_PENDING can transition to
        transitions.put(BookingStatus.PAYMENT_PENDING, EnumSet.of(
                BookingStatus.COMPLETED,
                BookingStatus.PAYMENT_FAILED
        ));
        
        // Terminal states
        transitions.put(BookingStatus.CANCELLED, EnumSet.noneOf(BookingStatus.class));
        transitions.put(BookingStatus.PAYMENT_FAILED, EnumSet.of(BookingStatus.PAYMENT_PENDING));
    }
    
    public boolean canTransition(BookingStatus from, BookingStatus to) {
        Set<BookingStatus> allowedTransitions = transitions.get(from);
        return allowedTransitions != null && allowedTransitions.contains(to);
    }
    
    public Set<BookingStatus> getAllowedTransitions(BookingStatus current) {
        return transitions.getOrDefault(current, EnumSet.noneOf(BookingStatus.class));
    }
}
