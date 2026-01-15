package tech.lemnova.continuum_backend.auth.emailToken;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationTokenRepository
    extends MongoRepository<EmailVerificationToken, String>
{
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUserId(String userId);
    void deleteByUserId(String userId);
}
