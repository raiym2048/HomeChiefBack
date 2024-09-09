package kg.nar.HomeChiefBack.dto.chief;

import lombok.Data;

import java.util.UUID;

@Data
public class ChiefInfoResponse {
    private UUID userId;
    private String phone;
    private String firstName;
    private String lastName;
    private String address;
}
