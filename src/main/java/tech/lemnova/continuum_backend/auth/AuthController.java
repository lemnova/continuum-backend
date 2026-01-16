package tech.lemnova.continuum_backend.auth;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.dtos.RegisterDTO;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserRepository;

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

    // Registro de usuário
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
        @Valid @RequestBody RegisterDTO dto
    ) {
        AuthResponseDTO response = authService.register(
            dto.username(),
            dto.email(),
            dto.password()
        );
        return ResponseEntity.ok(response);
    }

    // Login de usuário
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @Valid @RequestBody LoginDTO dto
    ) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    // Verificação de e-mail
    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(
        @RequestParam String token
    ) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(
            Map.of("message", "Email verified successfully")
        );
    }

    // Endpoint para pegar dados do usuário autenticado
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(
        @RequestHeader("Authorization") String authHeader
    ) {
        try {
            // Remove prefixo "Bearer "
            String token = authHeader.substring(7);

            // Valida o token JWT
            if (!jwtService.isTokenValid(token)) {
                return ResponseEntity.status(401)
                    .body(Map.of("message", "Invalid token"));
            }

            // Extrai userId do token
            String userId = jwtService.extractUserId(token);

            // Busca usuário no banco
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            // Retorna dados essenciais
            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(Map.of("message", "Invalid or missing token"));
        }
    }
}