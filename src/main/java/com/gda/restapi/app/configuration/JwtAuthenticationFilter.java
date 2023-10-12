package com.gda.restapi.app.configuration;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gda.restapi.app.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Optional<String> token = jwtService.getToken(request);
		
		if(request.getServletPath().contains("/api/v1/login") && token.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
		String username = null;
		if (token.isPresent()) {
			try {
				username = jwtService.extractUsername(token.get());
			} catch (Exception e) {}
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtService.isTokenValid(token.get(), userDetails)) {
				UsernamePasswordAuthenticationToken authentication = 
										new UsernamePasswordAuthenticationToken(
												userDetails.getUsername(), null, userDetails.getAuthorities());

				authentication.setDetails(
						new WebAuthenticationDetailsSource()
							.buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
