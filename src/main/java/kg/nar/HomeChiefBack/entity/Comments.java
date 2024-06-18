package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;
    @OneToOne
    private User user;

    @OneToOne
    private Food food;

    private String title;
}
