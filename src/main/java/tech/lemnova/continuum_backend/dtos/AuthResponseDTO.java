package tech.lemnova.continuum_backend.dtos;

import tech.lemnova.continuum_backend.dtos.user.UserDTO;

public record AuthResponseDTO(String token, UserDTO user){}