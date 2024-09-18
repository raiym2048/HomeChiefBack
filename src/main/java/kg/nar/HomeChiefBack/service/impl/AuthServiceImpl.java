package kg.nar.HomeChiefBack.service.impl;

import kg.nar.HomeChiefBack.config.JwtService;
import kg.nar.HomeChiefBack.dto.LoginRequest;
import kg.nar.HomeChiefBack.dto.RegisterRequest;
import kg.nar.HomeChiefBack.dto.auth.LoginResponse;
import kg.nar.HomeChiefBack.entity.Chief;
import kg.nar.HomeChiefBack.entity.Client;
import kg.nar.HomeChiefBack.entity.RequestStatus;
import kg.nar.HomeChiefBack.entity.User;
import kg.nar.HomeChiefBack.enums.ChiefRank;
import kg.nar.HomeChiefBack.enums.Role;
import kg.nar.HomeChiefBack.exception.BadCredentialsException;
import kg.nar.HomeChiefBack.exception.BadRequestException;
import kg.nar.HomeChiefBack.exception.NotFoundException;
import kg.nar.HomeChiefBack.repository.ChiefRepository;
import kg.nar.HomeChiefBack.repository.ClientRepository;
import kg.nar.HomeChiefBack.repository.RequestStatusRepository;
import kg.nar.HomeChiefBack.repository.UserRepository;
import kg.nar.HomeChiefBack.service.AuthService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
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
    private final RequestStatusRepository requestStatusRepository;
    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber()))
            throw new BadRequestException("User with this phone number already exists");
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new BadRequestException("User with this username already exists");
        User user = new User();
        user.setUsername(registerRequest.getUsername());
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

        Optional<User> user = userRepository.findByPhoneNumber(registerRequest.getPhoneNumber());
        String token;
        if (user.isEmpty())
            throw new BadRequestException("User with this phone number does not exist");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getPhoneNumber(), registerRequest.getPassword()));
            token = jwtService.generateToken(user.get());
        } catch (Exception e) {
            throw new BadRequestException("Invalid email or password");
        }
        if (user.get().getRole().equals(Role.CLIENT))
            return new LoginResponse(user.get().getClient().getFirstname(), user.get().getClient().getLastname(),  user.get().getId(), token, Role.CLIENT.name());
        return new LoginResponse(user.get().getChief().getFirstname(), user.get().getChief().getLastname(),  user.get().getId(), token, Role.CHIEF.name());
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
        return userRepository.findByPhoneNumber(String.valueOf(object.get("sub"))).orElseThrow(() -> new BadCredentialsException("No user in database with ur token! ReLogIn pls"));
    }

    private Client registerClient() {
        Client client = new Client();
        return clientRepository.save(client);
    }

    private Chief registerChief() {
        Chief chief = new Chief();
        chief.setRank(ChiefRank.NOT_VERIFIED);
        Optional<RequestStatus> requestStatusOptional = requestStatusRepository.findByStatus("not verified");
        if (requestStatusOptional.isEmpty())
            throw new NotFoundException("status not found, manager did not yet", HttpStatus.NOT_FOUND);
        chief.setActivated(requestStatusOptional.get());
        return chiefRepository.save(chief);
    }
}
