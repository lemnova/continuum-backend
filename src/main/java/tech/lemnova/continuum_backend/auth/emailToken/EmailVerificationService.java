package tech.lemnova.continuum_backend.auth.emailToken;

import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserService userService;

    public EmailVerificationService(
        EmailVerificationTokenRepository tokenRepository,
        EmailService emailService,
        UserService userService
    ) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    public void createAndSend(User user) {
        EmailVerificationToken token = new EmailVerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(token);
        emailService.sendVerificationEmail(
            user.getEmail(),
            token.getToken()
        );
    }

    public void verify(String tokenValue) {
        EmailVerificationToken token = tokenRepository
            .findByToken(tokenValue)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = token.getUser();
        userService.activate(user.getId());

        token.setUsed(true);
        tokenRepository.delete(token);
    }
}