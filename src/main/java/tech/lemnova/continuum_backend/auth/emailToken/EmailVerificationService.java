package tech.lemnova.continuum_backend.auth.emailToken;

import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserRepository;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public EmailVerificationService(
        EmailVerificationTokenRepository tokenRepository,
        UserRepository userRepository,
        EmailService emailService
    ) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void createAndSendVerificationToken(User user) {
        String tokenValue = UUID.randomUUID().toString();

        EmailVerificationToken token = new EmailVerificationToken();
        token.setToken(tokenValue);
        token.setUserId(user.getId());
        token.setExpiresAt(Instant.now().plusSeconds(86400)); // 24 horas
        token.setCreatedAt(Instant.now());

        tokenRepository.save(token);
        emailService.sendVerificationEmail(user.getEmail(), tokenValue);
    }

    @Transactional
    public void verifyToken(String tokenValue) {
        EmailVerificationToken token = tokenRepository
            .findByToken(tokenValue)
            .orElseThrow(() ->
                new RuntimeException("Invalid verification token")
            );

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Verification token has expired");
        }

        User user = userRepository
            .findById(token.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true);
        userRepository.save(user);

        tokenRepository.delete(token);
    }
}
