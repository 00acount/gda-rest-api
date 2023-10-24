package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.userWithoutPasswordFilter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.exception.ResourceForbiddenException;
import com.gda.restapi.app.model.Role;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	// GET '/users' //
	@GetMapping("/users")
	public ResponseEntity<MappingJacksonValue> retrieveAllUsers(Authentication authentication) {
		List<User> users = new ArrayList<>();
		var isAdmin = isAdmin(authentication);
		
		if (isAdmin)
			users = userService.getUsersByRole(Role.USER);
		else
			users = userService.getAll();
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(users);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// POST '/users' //
	@PostMapping("/users")
	public ResponseEntity<MappingJacksonValue> createUser(@RequestBody @Valid User user, Authentication authentication) {
		
		var isAdmin = isAdmin(authentication);
		
		if (isAdmin && !user.getRole().equals(Role.USER.name()))
			throw new ResourceForbiddenException("You don't have privilege to access these resources");

		User savedUser = userService.create(user);

		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedUser.getId()).build().toUri();
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(user);

		
		return ResponseEntity.created(location).body(mappingJacksonValue);
	}


	// GET '/users/{id}' //
	@GetMapping("/users/{id}")
	public ResponseEntity<MappingJacksonValue> retrieveUser(@PathVariable String id, Authentication authentication) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		User user = userService.getById(intId);
		var isAdmin = isAdmin(authentication);
	
		if (isAdmin && !user.getRole().equals(Role.USER.name()))
			throw new ResourceForbiddenException("You can't access these resources");
			
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(user);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// PUT '/users/{id}' //
	@PutMapping("/users/{id}")
	public ResponseEntity<MappingJacksonValue> updateUser(@PathVariable String id, @RequestBody @Valid User user, Authentication authentication) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		var registeredUser = userService.getById(intId);
		var isAdmin = isAdmin(authentication);

		if (isAdmin && !registeredUser.getRole().equals(Role.USER.name()))
			throw new ResourceForbiddenException("You can't update these resources");

		
		user.setId(intId);
		user.setRole(registeredUser.getRole());
		User updatedUser = userService.update(user);

		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(updatedUser);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// DELETE '/users/{id}' //
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id, Authentication authentication) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}

		var user = userService.getById(intId);
		var isAdmin = isAdmin(authentication);

		if (isAdmin && !user.getRole().equals(Role.USER.name()))
			throw new ResourceForbiddenException("You can't delete these resources");

		userService.deleteById(intId);

		return ResponseEntity.noContent().build();
	}

	
	private boolean isAdmin(Authentication authentication) {
		var roles =  authentication.getAuthorities();
		var isAdmin = roles.contains(
				new SimpleGrantedAuthority(Role.ADMIN.name()));
		return isAdmin;
	}

}
