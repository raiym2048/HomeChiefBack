package kg.nar.HomeChiefBack.dto.auth;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginResponse {
    private String firstname;
    private String lastname;
    private String email;
    private UUID userId;
    private String token;

    public LoginResponse(String firstname, String lastname, String email, UUID userId, String token) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userId = userId;
        this.token = token;
    }
}
