package tech.lemnova.continuum_backend.auth.dtos;

public record AuthResponseDTO(
    String token,
    String userId,
    String username,
    String email
) {}
