package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
    @SequenceGenerator(name = "food_seq", sequenceName = "food_seq", initialValue = 10000, allocationSize = 1)
    private Long id;

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



}
