package kg.nar.HomeChiefBack.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long jwtLifeTime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        String role = userDetails.getAuthorities().toString();
        claims.put("role", role);

        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + jwtLifeTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .signWith(signWith())
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signWith())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmailFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return (userEmail.equals(getUserEmailFromToken(token)) && expiration.after(new Date()));
    }

    private Key signWith() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
