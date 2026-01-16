package tech.lemnova.continuum_backend.auth.emailToken;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.SendEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final Resend resend;

    @Value("${email.from}")
    private String fromEmail;

    @Value("${email.verification.url}")
    private String emailVerificationURL;

    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendVerificationEmail(String email, String token) {
        String link = emailVerificationURL + "/auth/verify?token=" + token;

        SendEmailRequest request = SendEmailRequest.builder()
            .from(fromEmail)
            .to(email)
            .subject("Verify your email")
            .html(buildHtml(link))
            .build();

        try {
            resend.emails().send(request);
            log.info("Verification email sent to {}", email);
        } catch (ResendException e) {
            log.error("Failed to send verification email to {}", email, e);
            throw new IllegalStateException("Email service unavailable");
        }
    }

    private String buildHtml(String link) {
        return """
        <html>
          <body style="font-family: Arial; background:#f3fdf6; padding:40px;">
            <div style="max-width:520px; background:#fff; padding:32px; border-radius:10px;">
              <h2 style="color:#2e7d32;">Welcome 👋</h2>
              <p>Click the button below to verify your email.</p>
              <div style="text-align:center; margin:30px 0;">
                <a href="%s"
                   style="padding:14px 28px; background:#4caf50; color:#fff;
                   text-decoration:none; border-radius:6px; font-weight:bold;">
                   Verify Email
                </a>
              </div>
              <p>If the button doesn’t work, use this link:</p>
              <p style="word-break:break-all;">%s</p>
            </div>
          </body>
        </html>
        """.formatted(link, link);
    }
}
