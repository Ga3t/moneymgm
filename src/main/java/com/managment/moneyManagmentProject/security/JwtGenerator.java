package com.managment.moneyManagmentProject.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
    

	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateToken(Authentication authentication, Long userId) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
    
        return Jwts.builder()
                   .setSubject(username)
                   .claim("id", userId)
                   .setIssuedAt(currentDate)
                   .setExpiration(expDate)
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }
    public Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return claims.get("id", Long.class);  
    }
    public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}
    public static Key getKey() {
        return key;
    }
}

