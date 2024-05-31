package kg.nar.HomeChiefBack.service;

import kg.nar.HomeChiefBack.dto.LoginRequest;
import kg.nar.HomeChiefBack.dto.RegisterRequest;
import kg.nar.HomeChiefBack.dto.auth.LoginResponse;
import kg.nar.HomeChiefBack.entity.User;

public interface AuthService {
    void register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest registerRequest);

    User getUsernameFromToken(String token);
}
