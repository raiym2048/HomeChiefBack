package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Bucket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = CascadeType.REMOVE)  // Каскадное удаление
    @JoinColumn(name = "food_id")
    private Food food;

    private int count;  // Number of each food item in the bucket
}
