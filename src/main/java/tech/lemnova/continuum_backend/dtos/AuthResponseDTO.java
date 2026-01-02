package tech.lemnova.continuum_backend.dtos;

public class AuthResponseDTO {

    public String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }
}
