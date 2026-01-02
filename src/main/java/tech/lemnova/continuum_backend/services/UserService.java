package tech.lemnova.continuum_backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.dtos.user.UserDTO;
import tech.lemnova.continuum_backend.entities.User;
import tech.lemnova.continuum_backend.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registred");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = repository.save(user);
        return "User created with ID: " + savedUser.getId();
    }

    public UserDTO read(long id) {
        User user = repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.from(user);
    }

    public String update(long id, User user) {
        User userEntity = repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Se trocar email, valida
        if (
            user.getEmail() != null &&
            !user.getEmail().equals(userEntity.getEmail()) &&
            repository.existsByEmail(user.getEmail())
        ) {
            throw new IllegalArgumentException("Email already registred");
        }

        userEntity.setEmail(
            user.getEmail() != null ? user.getEmail() : userEntity.getEmail()
        );
        userEntity.setUsername(
            user.getUsername() != null
                ? user.getUsername()
                : userEntity.getUsername()
        );
        if (user.getPassword() != null){
            userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        

        repository.saveAndFlush(userEntity);

        return "User: " + read(id) + "Updated";
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
