package com.urbanclone.booking.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingAddress {
    
    private String street;
    
    private String city;
    
    private String state;
    
    private String country;
    
    private String zipCode;
    
    private Double latitude;
    
    private Double longitude;
    
    private String apartmentNumber;
    
    private String landmark;
}
