package tech.lemnova.continuum_backend.dtos.user;

import tech.lemnova.continuum_backend.entities.User;

public record UserDTO(long id, String name, String email, Boolean status) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getStatus());
    }
}
