package kg.nar.HomeChiefBack.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String password;
    private String phoneNumber;
    private Boolean isChief;
}
