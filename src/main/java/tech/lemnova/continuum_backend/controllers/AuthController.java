package tech.lemnova.continuum_backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.lemnova.continuum_backend.dtos.AuthResponseDTO;
import tech.lemnova.continuum_backend.dtos.LoginDTO;
import tech.lemnova.continuum_backend.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginDTO dto) {
        String token = authService.login(dto.email, dto.password);
        return new AuthResponseDTO(token);
    }
}
