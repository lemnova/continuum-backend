package tech.lemnova.continuum_backend.repositorys;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.lemnova.continuum_backend.entitys.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
