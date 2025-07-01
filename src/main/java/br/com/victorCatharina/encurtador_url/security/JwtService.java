package br.com.victorCatharina.encurtador_url.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    public static final long EXPIRATION = 1000 * 60 * 60 * 24;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isBlank())
            throw new IllegalStateException("JWT secret is missing! Check environment variable JWT_SECRET or application.properties.");

        if (jwtSecret.length() < 32)
            throw new IllegalStateException("JWT secret is too short! It should be at least 32 characters for HS256.");
    }

    public String generateToken(String username) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION)) // 24h
                .build();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT jwt = new SignedJWT(header, claims);

        jwt.sign(new MACSigner(jwtSecret.getBytes()));

        return jwt.serialize();
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            boolean verified = jwt.verify(new MACVerifier(jwtSecret.getBytes()));

            String username = jwt.getJWTClaimsSet().getSubject();
            Date expiration = jwt.getJWTClaimsSet().getExpirationTime();

            return verified && username.equals(expectedUsername) && expiration.after(new Date());

        } catch (Exception e) {
            log.error("Erro ao validar Token", e);
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            log.error("Erro ao extrair Username", e);
            return "";
        }
    }

    public long extractExpiration(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getExpirationTime().getTime();
        } catch (Exception e) {
            log.error("Erro ao extrair expiração");
            return 0L;
        }
    }
}