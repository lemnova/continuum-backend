package tech.lemnova.continuum_backend.repositories;

import jakarta.transaction.Transactional;
import tech.lemnova.continuum_backend.entities.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
