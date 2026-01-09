package tech.lemnova.continuum_backend.auth.dtos;

public record LoginDTO(
    String email,
    String password
){}
