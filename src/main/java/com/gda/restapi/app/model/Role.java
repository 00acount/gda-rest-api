package com.gda.restapi.app.model;

public enum Role {
	ADMIN("ADMIN"), USER("USER"), SUPER_ADMIN("SUPER_ADMIN");
	
	private String name;
	private Role(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
