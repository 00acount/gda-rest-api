package com.gda.restapi.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/refresh-xsrf")
	public void refreshXsrf(HttpServletResponse response) {
		response.addHeader("Set-Cookie", "SIDCC=1; expires=Thu, 10-Oct-2024 15:55:59 GMT; path=/; domain=gdaapi.onrender.com");
		response.addHeader("Set-Cookie", "SIDCC=2; expires=Thu, 10-Oct-2024 15:55:59 GMT; path=/; domain=.gdaapi.onrender.com");
		response.addHeader("Set-Cookie", "SIDCC=3; expires=Thu, 10-Oct-2024 15:55:59 GMT; path=/; domain=onrender.com");
		response.addHeader("Set-Cookie", "SIDCC=4; expires=Thu, 10-Oct-2024 15:55:59 GMT; path=/; domain=.onrender.com");
	}
	
	@PostMapping("/login")
	public void login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request, HttpServletResponse response) {
		authenticationService.authenticate(authenticationRequest);
		jwtService.setToken(response);
	}
}
