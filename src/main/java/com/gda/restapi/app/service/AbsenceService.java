package com.gda.restapi.app.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Absence;
import com.gda.restapi.app.model.Role;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.AbsenceRepository;
import com.gda.restapi.app.repository.SessionRepository;
import com.gda.restapi.app.repository.UserRepository;

@Service
public class AbsenceService {
	
	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	public List<Absence> getAbsencesSessionForUser(int userId, int sessionId) {
		User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " doesn't exist"));
		
		Session session = sessionRepository.findById(sessionId)
					.orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " doesn't exist"));
		
		if (session.getUser().getId() != userId && user.getRole().equals(Role.USER.name()))
			throw new ResourceNotFoundException("Session with ID " + sessionId + " doesn't belong to User with ID " + userId);

		return absenceRepository.getAbsencesBySessionId(sessionId);
	}

	public List<Absence> UpdateAbsenceStatusForSession(int userId, int sessionId, List<Absence> absences) {
		getAbsencesSessionForUser(userId, sessionId);
		return absenceRepository.saveAll(absences);
	}
	
}
