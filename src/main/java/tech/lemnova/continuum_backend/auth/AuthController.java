package tech.lemnova.continuum_backend.auth;

import jakarta.validation.Valid;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.dtos.RegisterDTO;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(
        AuthService authService,
        UserRepository userRepository,
        JwtService jwtService
    ) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterDTO dto) {
        AuthResponseDTO response = authService.register(dto.username(), dto.email(), dto.password());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(Map.of("message", "Email verified successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    // ✅ NOVO ENDPOINT - GET USER FROM TOKEN
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(
        @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // Extrair token do header "Bearer TOKEN"
            String token = authHeader.substring(7);
            
            // Extrair username do token
            String username = jwtService.extractUsername(token);
            
            // Buscar usuário no banco
            User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Retornar dados do usuário
            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "active", user.getActive()
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "Invalid or expired token"
            ));
        }
    }
}