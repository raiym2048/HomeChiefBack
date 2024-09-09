package kg.nar.HomeChiefBack.controller;


import kg.nar.HomeChiefBack.dto.LoginRequest;
import kg.nar.HomeChiefBack.dto.RegisterRequest;
import kg.nar.HomeChiefBack.dto.auth.LoginResponse;
import kg.nar.HomeChiefBack.dto.chief.AddressRequest;
import kg.nar.HomeChiefBack.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest registerRequest) {
        return authService.login(registerRequest);
    }

}
