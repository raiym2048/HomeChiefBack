package kg.nar.HomeChiefBack.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int star;

    @OneToOne
    private Food food;
    @OneToOne
    private User user;
}
