package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.userFilter;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.service.SessionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class SessionController {
	
	@Autowired
	private SessionService sessionService;

	// GET '/users/sessions' //
	@GetMapping("/admin/users/sessions")
	public ResponseEntity<MappingJacksonValue> retrieveAllSessions() {
		List<Session> sessions = sessionService.getAllSessions();
		
		MappingJacksonValue mappingJacksonValue = userFilter(sessions);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}
	

	//	Post '/users/{id}/sessions' //
	@PostMapping("user/users/{id}/sessions")
	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<MappingJacksonValue> createSession(@PathVariable String id, @RequestBody @Valid Session session) {
		int userId = 0;
		try {
			userId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		
		Session savedSession = sessionService.createSession(userId, session);

		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedSession.getId()).build().toUri();

		MappingJacksonValue mappingJacksonValue = userFilter(savedSession);

		return ResponseEntity.created(location).body(mappingJacksonValue);
	}


	//	GET '/users/{id}/sessions' //
	@GetMapping("user/users/{id}/sessions")
//	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<MappingJacksonValue> retrieveUserSessions(@PathVariable String id) {
		int intId = 0;
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the user ID as an integer number");
		}
		List<Session> sessions = sessionService.getSessionsByUser(intId);
		MappingJacksonValue mappingJacksonValue = userFilter(sessions);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}


	//	GET '/users/{id}/sessions/{sid}' //
	@GetMapping("user/users/{id}/sessions/{sid}")
	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<MappingJacksonValue> retrieveUserSession(@PathVariable String id, @PathVariable String sid) {
		int userId = 0, sessionId = 0;
		try {
			userId = Integer.valueOf(id);
			sessionId = Integer.valueOf(sid);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("Both the session ID or the user ID must be an integer");
		}

		Session session = sessionService.getById(userId, sessionId);
		MappingJacksonValue mappingJacksonValue = userFilter(session);

		return ResponseEntity.ok(mappingJacksonValue);
	}


	//	DELETE '/users/{id}/sessions/{sid}' //
	@DeleteMapping("user/users/{id}/sessions/{sid}")
//	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<Void> deleteUserSession(@PathVariable String id, @PathVariable String sid) {
		int sessionId = 0;
		try {
			Integer.valueOf(id);
			sessionId = Integer.valueOf(sid);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("Both the session ID or the user ID must be an integer");
		}

		sessionService.deleteById(sessionId);

		return ResponseEntity.noContent().build();
	}

}
