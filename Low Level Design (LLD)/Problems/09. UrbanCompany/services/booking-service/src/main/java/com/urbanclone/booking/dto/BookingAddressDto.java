package com.urbanclone.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingAddressDto {
    
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
