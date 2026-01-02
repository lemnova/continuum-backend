package tech.lemnova.continuum_backend.services;

import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.entities.User;

@Service
public class JwtService {

    private static final String SECRET = "secret-key-test-super-hyper-mega-secret-3000-11-generation-hard-tech";
    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    public String generateToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId().toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
            .compact();
    }

    public Long extractUserId(String token) {
        return Long.parseLong(
            Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
        );
    }
}
