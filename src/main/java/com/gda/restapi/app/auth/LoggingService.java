package com.gda.restapi.app.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.gda.restapi.app.repository.UserRepository;

@Component
public class LoggingService {
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired UserRepository userRepository;	
	@Autowired UserDetailsService userDetailsService;

	public void authenticate(AuthenticationRequest request) {
		var user = userRepository.findByEmail(request.getEmail()).orElse(null);
	
		if (user != null) {
			var authentication = new UsernamePasswordAuthenticationToken(
							request.getEmail(), request.getPassword(), user.getAuthorities());
			
			authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			user.setLastSeen(LocalDateTime.now());
			user.setIsOnline(true);
			userRepository.save(user);
		}
	}
	
	public void logoutHandler(Authentication authentication) {

		var email = (String) authentication.getPrincipal();
		var user = userRepository.findByEmail(email).get();

		user.setIsOnline(false);
		user.setLastSeen(LocalDateTime.now());
		userRepository.save(user);
		
		SecurityContextHolder.clearContext();
	}


}
