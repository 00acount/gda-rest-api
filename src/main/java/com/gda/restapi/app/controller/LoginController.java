package com.gda.restapi.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gda.restapi.app.auth.AuthenticationRequest;
import com.gda.restapi.app.auth.AuthenticationService;
import com.gda.restapi.app.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
	
	@Autowired private JwtService jwtService;
	@Autowired private AuthenticationService authenticationService;

	
	@PostMapping("/login")
	public void login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
		authenticationService.authenticate(authenticationRequest);
		jwtService.setToken(response);
	}
}
