package BookMyShow.BookMyShow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;
}
