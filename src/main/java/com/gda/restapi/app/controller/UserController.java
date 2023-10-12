package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.userWithoutPasswordFilter;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
	public ResponseEntity<MappingJacksonValue> retrieveAllUsers() {
		List<User> users = userService.getAll();
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(users);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// POST '/users' //
	@PostMapping("/users")
	public ResponseEntity<MappingJacksonValue> createUser(@RequestBody @Valid User user) {
		User savedUser = userService.create(user);
		
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedUser.getId()).build().toUri();
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(user);
		
		return ResponseEntity.created(location).body(mappingJacksonValue);
	}


	// GET '/users/{id}' //
	@GetMapping("/users/{id}")
	public ResponseEntity<MappingJacksonValue> retrieveUser(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		User user = userService.getById(intId);
		
		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(user);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// PUT '/users/{id}' //
	@PutMapping("/users/{id}")
	public ResponseEntity<MappingJacksonValue> updateUser(@PathVariable String id, @RequestBody @Valid User user) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		user.setId(intId);
		User updatedUser = userService.update(user);

		MappingJacksonValue mappingJacksonValue = 
							userWithoutPasswordFilter(updatedUser);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	// DELETE '/users/{id}' //
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		userService.deleteById(intId);

		return ResponseEntity.noContent().build();
	}

}
