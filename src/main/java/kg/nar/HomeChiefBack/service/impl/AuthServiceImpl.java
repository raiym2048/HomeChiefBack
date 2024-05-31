package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.config.JwtService;
import kg.nar.HomeChiefBack.dto.LoginRequest;
import kg.nar.HomeChiefBack.dto.RegisterRequest;
import kg.nar.HomeChiefBack.dto.auth.LoginResponse;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.Client;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.ChiefRank;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadCredentialsException;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.repository.ChiefRepository;
import kg.nar.HomeChiefBack.repository.ClientRepository;
import kg.nar.HomeChiefBack.repository.UserRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ChiefRepository chiefRepository;
    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new BadRequestException("User with this email already exists");
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber()))
            throw new BadRequestException("User with this phone number already exists");
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        if (registerRequest.getIsChief()){
            user.setRole(Role.CHIEF);
            user.setChief(registerChief());
        }
        else {
            user.setRole(Role.CLIENT);
            user.setClient(registerClient());
        }
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest registerRequest) {
        if (!userRepository.existsByEmail(registerRequest.getEmail()))
            throw new BadRequestException("User with this email does not exist");
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());
        String token;
        if (user.isEmpty())
            throw new BadRequestException("User with this email does not exist");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));
            token = jwtService.generateToken(user.get());
        } catch (Exception e) {
            throw new BadRequestException("Invalid email or password");
        }
        if (user.get().getRole().equals(Role.CLIENT))
            return new LoginResponse(user.get().getClient().getFirstname(), user.get().getClient().getLastname(), registerRequest.getEmail(), user.get().getId(), token);
        return new LoginResponse(user.get().getChief().getFirstname(), user.get().getChief().getLastname(), registerRequest.getEmail(), user.get().getId(), token);
    }

    @Override
    public User getUsernameFromToken(String token) {

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(decoder.decode(chunks[1]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByEmail(String.valueOf(object.get("sub"))).orElseThrow(() -> new BadCredentialsException("No user in database with ur token! ReLogIn pls"));
    }

    private Client registerClient() {
        Client client = new Client();
        return clientRepository.save(client);
    }

    private Chief registerChief() {
        Chief chief = new Chief();
        chief.setActivated(false);
        chief.setRank(ChiefRank.CHIEF);
        return chiefRepository.save(chief);
    }
}
