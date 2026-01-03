package tech.lemnova.continuum_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.dtos.LoginDTO;
import tech.lemnova.continuum_backend.entities.User;
import tech.lemnova.continuum_backend.services.AuthService;
import tech.lemnova.continuum_backend.services.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(
        AuthService authService,
        UserService userService
    ) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @RequestBody LoginDTO dto
    ) {
        return ResponseEntity.ok(
            authService.login(dto.email(), dto.password())
        );
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
        @RequestBody User user
    ) {
        return ResponseEntity.ok(
            userService.create(user)
        );
    }
}