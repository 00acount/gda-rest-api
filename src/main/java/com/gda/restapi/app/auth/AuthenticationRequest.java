package com.gda.restapi.app.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private String email;
	private String password;

}
