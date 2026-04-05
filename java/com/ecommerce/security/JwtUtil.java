package com.ecommerce.security;

import com.ecommerce.entity.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

	private final String SECRET = "mysecretkey"; // change for production
	private final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 hours

	public String generateToken(User user) {
		return Jwts.builder().setSubject(user.getEmail()).claim("role", user.getRole().name()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(SignatureAlgorithm.HS256, SECRET).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token, User user) {
		String email = extractUsername(token);
		return email.equals(user.getEmail()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getExpiration().before(new Date());
	}

	public String getEmailFromToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}