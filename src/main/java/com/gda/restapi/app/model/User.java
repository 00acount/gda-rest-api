package com.gda.restapi.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gda.restapi.app.annotation.CheckUserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserWithoutPass")
@Entity(name = "user_app")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 8726135811509899927L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "firstName is required and must consist of at least one letter")
	private String firstName;

	@NotBlank(message = "lastName is required and must consist of at least one letter")
	private String lastName;

	@Email(message = "Invalid email format")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "password is required, if you dont wanna change it put '$$_#@!#@' as a value")
	@Size(min = 8, message = "The password must consist of at least 8 letters")
	private String password;

	private LocalDate registeredOn;
	private LocalDateTime lastSeen;
	
	@CheckUserRole(regexp = "^(SUPER_ADMIN|ADMIN|USER)$", message = "The user must has a role 'ADMIN' or 'USER'")
	private String role;
	
	private boolean isOnline;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline; 
	}
	
	public boolean getIsOnline() {
		return this.isOnline;
	}

}
