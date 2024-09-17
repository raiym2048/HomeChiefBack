package kg.nar.HomeChiefBack.dto.chief;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressRequest {


    private String country;
    private String city;
    private String street;

    private String firstname;
    private String lastname;

}

