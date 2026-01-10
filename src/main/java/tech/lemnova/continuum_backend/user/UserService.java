package tech.lemnova.continuum_backend.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.user.dtos.UserDTO;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository repository,
        PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔒 Registro SEM ativar
    public String create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);

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

        if (
            user.getEmail() != null &&
            !user.getEmail().equals(userEntity.getEmail()) &&
            repository.existsByEmail(user.getEmail())
        ) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (user.getEmail() != null) {
            userEntity.setEmail(user.getEmail());
        }

        if (user.getUsername() != null) {
            userEntity.setUsername(user.getUsername());
        }

        if (user.getPassword() != null) {
            userEntity.setPassword(
                passwordEncoder.encode(user.getPassword())
            );
        }

        repository.saveAndFlush(userEntity);
        return "User updated";
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void activate(long id) {
        User user = repository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);
        repository.saveAndFlush(user);
    }
}