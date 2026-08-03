package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.exception.TokenGenerationException;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private String secret;

    public String generateToken(UserDetails u) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withIssuer("MindCracker").withSubject(u.getUsername()).withExpiresAt(generateExpiry()).sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenGenerationException("Error generating user Token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).withIssuer("MindCracker").build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Instant generateExpiry() {
        return LocalDateTime.now().plusDays(14).toInstant(ZoneOffset.of("+0"));
    }

    public Cookie generateCookie(String token) {
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(50400);
        return cookie;
    }
}
