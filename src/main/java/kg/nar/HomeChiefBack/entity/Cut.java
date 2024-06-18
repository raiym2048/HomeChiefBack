package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    private String url;
    private String name;
    private String description;

    private double stars;

    @ManyToOne
    private Food food;

    @OneToMany
    private List<Comments> comments;


}
