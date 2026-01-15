package tech.lemnova.continuum_backend.auth;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.emailToken.EmailVerificationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;

    public AuthController(
        AuthService authService,
        EmailVerificationService emailVerificationService
    ) {
        this.authService = authService;
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
        @RequestBody Map<String, String> request
    ) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");

        AuthResponseDTO response = authService.register(
            username,
            email,
            password
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}
