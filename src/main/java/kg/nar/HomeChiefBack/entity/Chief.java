package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import kg.nar.HomeChiefBack.enums.ChiefRank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Chief {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private ChiefRank rank;
    @ManyToOne
    private RequestStatus activated;
    @OneToOne
    private Address address;

    @ElementCollection
    List<Double> rating;

    public double getAverageRating() {
        return rating.stream()
                .mapToDouble(Double::doubleValue)  // Corrected method reference
                .average()
                .orElse(0.0);  // Return 0.0 if there are no ratings
    }


}
