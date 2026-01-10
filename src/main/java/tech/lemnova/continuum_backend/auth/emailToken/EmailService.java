package tech.lemnova.continuum_backend.auth.emailToken;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendVerificationEmail(String email, String token) {
        String link = "http://localhost:8080/auth/verify?token=" + token;
        
        Dotenv dotenv = Dotenv.load();

        final String fromEmail = dotenv.get("EMAIL_USER");
        final String password = dotenv.get("EMAIL_PASS");
        final String smtpHost = dotenv.get("SMTP_HOST");
        final String smtpPort = dotenv.get("SMTP_PORT");

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
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
            message.setSubject("Your Validation Code");

            String html
                    = "<html>"
                    + "<body style=\"margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f3fdf6;\">"
                    + "<div style=\"max-width:520px; margin:40px auto; background-color:#ffffff;"
                    + "padding:32px; border-radius:10px;"
                    + "box-shadow:0 8px 24px rgba(0,0,0,0.08);\">"
                    + "<h2 style=\"color:#2e7d32; margin-top:0;\">Welcome 👋</h2>"
                    + "<p style=\"color:#444; font-size:15px; line-height:1.6;\">"
                    + "You’re almost there. Click the button below to verify your email and activate your account."
                    + "</p>"
                    + "<div style=\"text-align:center; margin:30px 0;\">"
                    + "<a href=\"" + link + "\" "
                    + "style=\"display:inline-block; padding:14px 28px;"
                    + "background-color:#4caf50; color:#ffffff;"
                    + "text-decoration:none; font-weight:bold;"
                    + "border-radius:6px;\">"
                    + "Verify Email"
                    + "</a>"
                    + "</div>"
                    + "<p style=\"color:#666; font-size:14px;\">"
                    + "If the button doesn’t work, copy and paste this link into your browser:"
                    + "</p>"
                    + "<p style=\"word-break:break-all; color:#2e7d32; font-size:13px;\">"
                    + link
                    + "</p>"
                    + "<hr style=\"border:none; border-top:1px solid #e0e0e0; margin:30px 0;\">"
                    + "<p style=\"font-size:12px; color:#888;\">"
                    + "If you didn’t request this email, you can safely ignore it."
                    + "</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            message.setContent(html, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
