package tech.lemnova.continuum_backend.auth.emailToken;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.lemnova.continuum_backend.auth.emailToken.EmailVerificationToken;

public interface EmailVerificationTokenRepository
    extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByToken(String token);
}