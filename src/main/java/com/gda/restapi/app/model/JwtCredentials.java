package com.gda.restapi.app.model;

import lombok.Data;

@Data
public class JwtCredentials {

	public int id;
	public String name;
	public String role;

}