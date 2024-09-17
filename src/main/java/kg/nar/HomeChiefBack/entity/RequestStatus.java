package kg.nar.HomeChiefBack.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Entity
@Getter
@Setter
public class RequestStatus {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String status;

    public RequestStatus(UUID uuid, String notVerified) {
        this.id = uuid;
        this.status = notVerified;
    }

    public RequestStatus() {

    }
}
