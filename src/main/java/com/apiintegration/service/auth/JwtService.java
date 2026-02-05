package com.apiintegration.service.auth;

import com.apiintegration.entity.User.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    public static String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
}
