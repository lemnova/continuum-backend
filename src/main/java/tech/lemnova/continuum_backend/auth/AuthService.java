package tech.lemnova.continuum_backend.auth;

import java.time.Instant;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.emailToken.EmailVerificationService;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailVerificationService emailVerificationService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        EmailVerificationService emailVerificationService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailVerificationService = emailVerificationService;
    }

    public AuthResponseDTO register(
        String username,
        String email,
        String password
    ) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setActive(false);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        User savedUser = userRepository.save(user);

        emailVerificationService.createAndSendVerificationToken(savedUser);

        String token = jwtService.generateToken(
            savedUser.getId(),
            savedUser.getUsername()
        );

        return new AuthResponseDTO(
            token,
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail()
        );
    }

    public AuthResponseDTO login(LoginDTO dto) {
        User user = userRepository
            .findByEmail(dto.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.getActive()) {
            throw new RuntimeException(
                "Please verify your email before logging in"
            );
        }

        String token = jwtService.generateToken(
            user.getId(),
            user.getUsername()
        );

        return new AuthResponseDTO(
            token,
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }

    public void verifyEmail(String token) {
        emailVerificationService.verifyToken(token);
    }
}
