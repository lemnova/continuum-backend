package tech.lemnova.continuum_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.dtos.auth.AuthResponseDTO;
import tech.lemnova.continuum_backend.dtos.auth.LoginDTO;
import tech.lemnova.continuum_backend.entities.User;
import tech.lemnova.continuum_backend.services.auth.AuthService;
import tech.lemnova.continuum_backend.services.UserService;
import tech.lemnova.continuum_backend.services.auth.ValEmailService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    //private final ValEmailService valEmailService;

    public AuthController(
        AuthService authService,
        UserService userService
        //ValEmailService valEmailService
    ) {
        this.authService = authService;
        this.userService = userService;
        //this.valEmailService = valEmailService;
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
        //valEmailService.sendValEmail(user.getEmail());
        return ResponseEntity.ok(
            userService.create(user)
        );
    }
    
    /*public ResponseEntity<String> activateUser(String code, String email){
        
    }*/
}