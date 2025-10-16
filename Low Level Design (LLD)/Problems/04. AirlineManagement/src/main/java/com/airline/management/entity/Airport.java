package com.airline.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code; // e.g., "DEL" for Delhi Airport

    private String city;

    private String country;
}
