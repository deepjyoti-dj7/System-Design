package BookMyShow.BookMyShow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "screen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    private String name;
    private Integer totalSeats;
}