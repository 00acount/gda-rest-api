package com.gda.restapi.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gda.restapi.app.auth.AuthenticationRequest;
import com.gda.restapi.app.auth.JwtService;
import com.gda.restapi.app.auth.LoggingService;
import com.gda.restapi.app.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class LoggingController {
	
	@Autowired private JwtService jwtService;
	@Autowired private LoggingService loggingService;
	@Autowired UserRepository userRepository;

	
	@PostMapping("/login")
	public void login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
		loggingService.authenticate(authenticationRequest);
		jwtService.setToken(response);
	}
	
	@PostMapping("/logout")
	public void logout(Authentication authentication) {
		loggingService.logoutHandler(authentication);
	}
}
