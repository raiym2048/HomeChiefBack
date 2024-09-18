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
    private Long achievesCount = new Random().nextLong();
}
