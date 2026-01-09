package tech.lemnova.continuum_backend.auth.dtos;

import tech.lemnova.continuum_backend.user.dtos.UserDTO;

public record AuthResponseDTO(String token, UserDTO user){}