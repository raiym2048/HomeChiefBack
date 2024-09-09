package kg.nar.HomeChiefBack.dto.address;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponse {
    private UUID addressUid;
    private UUID parentUid;
    private String value;
}
