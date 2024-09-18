package kg.nar.HomeChiefBack.dto.auth;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginResponse {
    private String firstname;
    private String lastname;
    private UUID userId;
    private String token;
    private String role;

    public LoginResponse(String firstname, String lastname, UUID userId, String token, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.userId = userId;
        this.token = token;
        this.role = role;
    }
}
