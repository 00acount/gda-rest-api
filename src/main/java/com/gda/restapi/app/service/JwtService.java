package com.gda.restapi.app.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gda.restapi.app.model.JwtCredentials;
import com.gda.restapi.app.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;

@Component
public class JwtService {
	
	@Autowired
	private UserRepository userRepository;
	@Value("${application.security.jwt.secret-key}")
	private String SECRET_KEY;

	private final int TOKEN_MAX_AGE = 60 * 1000; 

	public Optional<String> getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || !token.startsWith("Bearer "))
			token = null;
		else
			token = token.replaceAll("Bearer ", "");
		
		return Optional.ofNullable(token);
	}
	
	public void setToken(HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder
											.getContext().getAuthentication();

		String username = (String) authentication.getPrincipal();
		String token = generateToken(new User(username, "", List.of()));
		
		response.setHeader("TOKEN", token);
	}
	
	public String generateToken(UserDetails userDetails) {
		return buildToken(new HashMap<>(), userDetails, TOKEN_MAX_AGE);
	}
	
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		var user = userRepository.findByEmail(userDetails.getUsername()).get();
		var credentials = new JwtCredentials();
			credentials.setId(user.getId());
			credentials.setName(user.getLastName());
			credentials.setRole(user.getRole());
			
		var objectMapper = new ObjectMapper();
		var subCredentials = "";
		try {
			subCredentials = objectMapper.writeValueAsString(credentials);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(subCredentials)
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
		var objectMapper = new ObjectMapper();
		String email = "";

		try {
			var credentials = objectMapper.readValue(subjectClaim.get(), JwtCredentials.class);
			var user = userRepository.findById(credentials.getId());
			email = user.get().getEmail();
			} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return email;
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
