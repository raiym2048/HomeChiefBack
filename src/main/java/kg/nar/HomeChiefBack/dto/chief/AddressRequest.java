package kg.nar.HomeChiefBack.dto.chief;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressRequest {

    private UUID parentId;

    private String type;
    private String value;

    private String firstname;
    private String lastname;
    private String status;

}

