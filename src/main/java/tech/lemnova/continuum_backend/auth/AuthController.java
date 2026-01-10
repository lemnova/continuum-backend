package tech.lemnova.continuum_backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.emailToken.EmailVerificationService;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    public AuthController(
        AuthService authService,
        UserService userService,
        EmailVerificationService emailVerificationService
    ) {
        this.authService = authService;
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered. Check your email.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(
        @RequestParam String token
    ) {
        emailVerificationService.verify(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @RequestBody LoginDTO dto
    ) {
        return ResponseEntity.ok(
            authService.login(dto.email(), dto.password())
        );
    }
}