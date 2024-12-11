package com.event_management.management.util;

import com.event_management.management.helpers.ErrorHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Signature;
import java.util.*;

@Component
public class JwtUtil {
    private final String SECRET_KEY = Base64.getEncoder().encodeToString("OMZL5Mto78ki+u6fyOu7s1XRjIX0l/Dixawmkt0TUjg=".getBytes());

    public String generateToken(String username, String role,String email,String userId) {
        try {
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",username);
            claims.put("email",email);
            claims.put("userId",userId);
            long thirtyDaysInMillis = 1000L * 60 * 60 * 24 * 30;
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .claim("role", role)
                    .setExpiration(new Date(System.currentTimeMillis() + thirtyDaysInMillis)) // 1 hour
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            System.out.println("Error generating token: " + e.getMessage());
            throw e;
        }
    }

    public Claims extractClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Extract roles from JWT Token
    public String extractRoles(String token) {
        Claims claims = extractClaims(token);
        System.out.println(claims);
        // Extract the "role" field directly as a String
        String role = (String) claims.get("role");
        if (role != null && !role.startsWith("ROLE_")) {
            return "ROLE_" + role.toUpperCase(); // Ensure proper format
        }
        return role;
    }

    public boolean isTokenValid(String token,String username){
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}
