package kg.nar.HomeChiefBack.entity;

import jakarta.persistence.*;
import kg.nar.HomeChiefBack.enums.ChiefRank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    private Boolean activated;
}
