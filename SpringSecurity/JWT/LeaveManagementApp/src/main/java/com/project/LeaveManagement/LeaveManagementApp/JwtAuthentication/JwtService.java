package com.project.LeaveManagement.LeaveManagementApp.JwtAuthentication;

import com.project.LeaveManagement.LeaveManagementApp.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(HashMap<String,Object> extraClaims, User user) {

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public <T>T extractClaim(String jwtToken, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaim(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String jwtToken) {
         return Jwts
                 .parser()
                 .verifyWith(getSignInKey())
                 .build()
                 .parseSignedClaims(jwtToken)
                 .getPayload();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public boolean isTokenValid(String jwtToken, User userDetails) {
        final String username = extractUsername(jwtToken);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
