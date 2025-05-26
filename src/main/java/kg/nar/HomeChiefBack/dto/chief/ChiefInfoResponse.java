package kg.nar.HomeChiefBack.dto.chief;

import lombok.Data;

import java.util.Random;
import java.util.UUID;

@Data
public class ChiefInfoResponse {
    private UUID userId;
    private UUID chiefId;
    private String phone;
    private String firstName;
    private String lastName;
    private String address;
    private Double rating;

    public ChiefInfoResponse() {
    }

    public ChiefInfoResponse(UUID userId, UUID chiefId, String phone, String firstName, String lastName, String address, Double rating, String image) {
        this.userId = userId;
        this.chiefId = chiefId;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.rating = rating;
        this.image = image;
    }

    private Long achievesCount = new Random().nextLong();
    private String image;
}
