package tech.lemnova.continuum_backend.dtos;

import tech.lemnova.continuum_backend.entitys.User;

public record UserDTO(long id, String name, String email) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}
