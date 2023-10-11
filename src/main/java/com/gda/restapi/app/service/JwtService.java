package com.gda.restapi.app.service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtService {
	
	public static final String COOKIE_NAME = "token";
	private final int TOKEN_MAX_AGE = 60 * 1000; 

	@Value("${frontend.domain}")
	private String frontendDomain;
	
	@Value("${application.security.jwt.secret-key}")
	private String SECRET_KEY;

	public Optional<String> getToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie token = null;
		

		if (cookies == null) 
			return Optional.empty();
		else 
			token = Arrays.stream(cookies)
				.filter(cookie -> cookie.getName().equals(COOKIE_NAME))
				.findFirst().orElse(new Cookie(COOKIE_NAME, null));
		
		return Optional.ofNullable(token.getValue());
	}
	
	public void setToken(HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder
											.getContext().getAuthentication();

		String username = (String) authentication.getPrincipal();
		String token = generateToken(new User(username, "", List.of()));
		
		Cookie cookie = new Cookie(COOKIE_NAME, token);
		cookie.setMaxAge(TOKEN_MAX_AGE);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setDomain("onrender.com");
		cookie.setPath("/");

		response.addCookie(cookie);
	}
	
	public String generateToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, TOKEN_MAX_AGE);
	}
	
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public String extractUsername(String token) {
		var subjectClaim = extractClaims(token, Claims::getSubject);
		return subjectClaim.isPresent()? subjectClaim.get(): null;
	}
	
	public boolean isTokenExpired(String token) {
		var expirationClaim =  extractClaims(token, Claims::getExpiration);
		return expirationClaim.isPresent()? expirationClaim.get().before(new Date()): true;
	}
	
	public <T> Optional<T> extractClaims(String token, Function<Claims, T> claimsResolver) {
		Claims claims = null;
		
		try {

			claims = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();

		} catch (ExpiredJwtException e) {
			claims = Jwts.claims(new HashMap<String, Object>());
		}
		
		return claimsResolver
				.andThen(value -> Optional
						.ofNullable(value)).apply(claims);
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
