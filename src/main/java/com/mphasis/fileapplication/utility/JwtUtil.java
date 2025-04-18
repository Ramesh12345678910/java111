package com.mphasis.fileapplication.utility;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
@Component
public class JwtUtil {
 
	public JwtUtil() {
	
	}


	private static final String SECRET_KEY = "hdihdiwdwe90i3123u933!@#!$#@$#@EXTRA";
    private static final long EXPIRATION_TIME = 3600000; 

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final JwtParser jwtParser = Jwts.parser().verifyWith(key).build();


   
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)  
                .issuedAt(new Date())  
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(key)  
                .compact();
    }

  
    public boolean validateToken(String token, String username) {
        Claims claims = extractClaims(token);
        System.out.println("Generating token for user: " + username);

        return username.equals(claims.getSubject()) && !isTokenExpired(claims);
    }

    
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

   
    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

   
    public Claims extractClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }
    
}
