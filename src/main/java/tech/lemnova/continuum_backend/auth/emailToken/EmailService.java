package tech.lemnova.continuum_backend.auth.emailToken;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private int smtpPort;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${app.backend.url}")
    private String backendUrl;

    public void sendVerificationEmail(String email, String token) {

        String link = backendUrl + "/auth/verify?token=" + token;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(
            props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(email)
            );
            message.setSubject("Verify your email");

            message.setContent(buildHtml(link), "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
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