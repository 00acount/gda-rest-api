package com.gda.restapi.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.gda.restapi.app.repository.UserRepository;

@Component
public class AuthenticationService {
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired UserRepository userRepository;	
	@Autowired UserDetailsService userDetailsService;

	public void authenticate(AuthenticationRequest request) {
//		var user = userDetailsService.loadUserByUsername(request.getEmail());
		var user = userRepository.findByEmail(request.getEmail()).get();
		
		var authentication = new UsernamePasswordAuthenticationToken(
						request.getEmail(), request.getPassword(), user.getAuthorities());
		
		authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		user.setIsOnline(true);
		userRepository.save(user);
	}


}
