package tech.lemnova.continuum_backend.user.dtos;

import tech.lemnova.continuum_backend.user.User;

public record UserDTO(
    String id,
    String username,
    String email,
    String role,
    Boolean active
) {
    public static UserDTO from(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getActive()
        );
    }
}
