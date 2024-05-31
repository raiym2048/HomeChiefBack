package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_type_seq")
    @SequenceGenerator(name = "food_type_seq", sequenceName = "food_type_seq", initialValue = 10000, allocationSize = 1)
    private Long id;
    private String name;


}
