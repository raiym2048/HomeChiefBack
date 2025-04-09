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

    public ChiefRank getRank() {
        return rank;
    }

    public void setRank(ChiefRank rank) {
        this.rank = rank;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public RequestStatus getActivated() {
        return activated;
    }

    public void setActivated(RequestStatus activated) {
        this.activated = activated;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Double> getRating() {
        return rating;
    }

    public void setRating(List<Double> rating) {
        this.rating = rating;
    }

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
