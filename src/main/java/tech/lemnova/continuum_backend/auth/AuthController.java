package tech.lemnova.continuum_backend.auth;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.auth.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.auth.dtos.LoginDTO;
import tech.lemnova.continuum_backend.auth.dtos.RegisterDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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

    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(
        @RequestParam String token
    ) {
        authService.verifyEmail(token);
        return ResponseEntity.ok(
            Map.of("message", "Email verified successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @Valid @RequestBody LoginDTO dto
    ) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(
        @RequestHeader("Authorization") String authHeader
    ) {
        // Remove "Bearer " prefix
        String token = authHeader.substring(7);
        // Aqui você pode adicionar lógica para retornar dados do usuário atual
        return ResponseEntity.ok(Map.of("message", "Authenticated"));
    }
}
