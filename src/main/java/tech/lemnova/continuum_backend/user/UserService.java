package tech.lemnova.continuum_backend.user;

import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.exception.ResourceNotFoundException;
import tech.lemnova.continuum_backend.user.dtos.UserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(String id) {
        User user = userRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id)
            );
        return UserDTO.from(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "User not found with username: " + username
                )
            );
        return UserDTO.from(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "User not found with email: " + email
                )
            );
        return UserDTO.from(user);
    }
}
