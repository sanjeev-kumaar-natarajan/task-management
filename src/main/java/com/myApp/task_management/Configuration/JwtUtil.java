package com.myApp.task_management.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<String, Object>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256) // Updated signing method
                .compact();

    }

    private Boolean validateToken(String token, String userName) {
        String extractedUserName = extractUserName(token);
        return (extractedUserName.equals(userName) && !isTokenExpired(token));
    }

    private String extractUserName(String token) {
        String extractedUserName = extractAllClaims(token).getSubject();
        return extractedUserName;
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}