package tech.lemnova.continuum_backend.user.dtos;

import tech.lemnova.continuum_backend.user.User;

public record UserDTO(long id, String name, String email, Boolean isActive) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getIsActive());
    }
}
