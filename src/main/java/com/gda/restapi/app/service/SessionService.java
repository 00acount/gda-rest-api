package com.gda.restapi.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Module;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.ModuleRepository;
import com.gda.restapi.app.repository.SectorRepository;
import com.gda.restapi.app.repository.SessionRepository;
import com.gda.restapi.app.repository.UserRepository;

@Service
public class SessionService {

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SectorRepository sectorRepository;
	@Autowired
	private ModuleRepository moduleRepository;
	
	
	public long getCount() {
		return sessionRepository.count();
	}
	
	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}

	public List<Session> getSessionsByUser(int id) {
		return sessionRepository.getSessionsByUserId(id);
	}

	public Session createSession(int userId, Session session) {
		User user = userRepository.findById(userId)
							.orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " doesn't exist"));
		Sector sector = sectorRepository.findById(session.getSector().getId())
							.orElseThrow(() -> new ResourceNotFoundException("Sector with ID " + session.getSector().getId() + " doesn't exist"));
		Module module = moduleRepository.findById(session.getModule().getId())
							.orElseThrow(() -> new ResourceNotFoundException("Module with ID " + session.getModule().getId() + " doesn't exist"));

		session.setUser(user);
		session.setSector(sector);
		session.setModule(module);
		session.setCreatedAt(LocalDate.now());
		
		Session savedSession = sessionRepository.save(session);
		return savedSession;
	}
	
	public Session getById(int userId, int sessionId) {
		userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " doesn't exist"));
		
		Session session = sessionRepository.findById(sessionId)
					.orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " doesn't exist"));
		
		if (session.getUser().getId() != userId)
			throw new ResourceNotFoundException("Session with ID " + sessionId + " doesn't belong to User with ID " + userId);
		
		return session;
	}

	public void deleteById(int sessionId) {
		sessionRepository.deleteById(sessionId);
	}
}
