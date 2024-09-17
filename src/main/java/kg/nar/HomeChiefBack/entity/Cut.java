package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Cut {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;


    private String url;
    private String name;
    private String description;

    private double stars;

    @ManyToOne
    private Food food;

    @OneToMany
    private List<Comments> comments;


}
