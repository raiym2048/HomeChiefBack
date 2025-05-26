package kg.nar.HomeChiefBack.dto.chief;

import kg.nar.HomeChiefBack.dto.address.AddressDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private String firstname;
    private String lastname;
    private AddressDTO address;
}
