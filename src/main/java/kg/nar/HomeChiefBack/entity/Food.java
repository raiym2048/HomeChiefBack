package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String description;
    private int price;
    private int discount;
    @ElementCollection
    private List<String> images;
    @ManyToOne
    private FoodType foodType;

    @ManyToOne
    private Chief chief;

    @OneToMany
    private List<Review> reviews;

    @ManyToMany
    private List<User> likedUsers;

    @OneToMany
    private List<Comments> comments;
    @ManyToMany
    private List<User> views;




}
