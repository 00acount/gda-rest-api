package com.gda.restapi.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Absence;
import com.gda.restapi.app.model.Module;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.AbsenceRepository;
import com.gda.restapi.app.repository.ModuleRepository;
import com.gda.restapi.app.repository.SectorRepository;
import com.gda.restapi.app.repository.SessionRepository;
import com.gda.restapi.app.repository.StudentRepository;
import com.gda.restapi.app.repository.UserRepository;

@Service
public class SessionService {

	@Autowired private SessionRepository sessionRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private StudentRepository studentRepository;
	@Autowired private SectorRepository sectorRepository;
	@Autowired private ModuleRepository moduleRepository;
	@Autowired private AbsenceRepository absenceRepository;
	
	
	public long getCount() {
		return sessionRepository.count();
	}
	
	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}

	public List<Session> getSessionsByUser(int id) {
		return sessionRepository.findByUserId(id);
	}

	public Session createSession(int userId, Session session) {
		User user = userRepository.findById(userId)
							.orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " doesn't exist"));
		Sector sector = sectorRepository.findById(session.getSector().getId())
							.orElseThrow(() -> new ResourceNotFoundException("Sector with ID " + session.getSector().getId() + " doesn't exist"));
		Module module = moduleRepository.findById(session.getModule().getId())
							.orElseThrow(() -> new ResourceNotFoundException("Module with ID " + session.getModule().getId() + " doesn't exist"));
		


		session.setId(null);
		session.setUser(user);
		session.setSector(sector);
		session.setModule(module);
		session.setCreatedAt(LocalDate.now());

		Session savedSession = sessionRepository.save(session);
		
		var students = studentRepository.findAllBySectorId(sector.getId());
		var absenceList =  students.stream().map(student -> 
									new Absence(student, savedSession, "PRESENT")).toList();
		
		absenceRepository.saveAll(absenceList);
		return savedSession;
	}
	
	public Session getById(int userId, int sessionId) {
		userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " doesn't exist"));
		
		Session session = sessionRepository.findById(sessionId)
					.orElseThrow(() -> new ResourceNotFoundException("Session with ID " + sessionId + " doesn't exist"));
		
		if (session.getUser().getId() != userId) {
			throw new ResourceNotFoundException("Session with ID " + sessionId + " doesn't belong to User with ID " + userId);
		}

		return session;
	}

	public void deleteById(int sessionId) {
		absenceRepository.deleteAbsencesBySessionId(sessionId);
		sessionRepository.deleteById(sessionId);
	}
	
	public void deleteByUserId(int userId) {
		List<Session> sessions = this.getSessionsByUser(userId);
		
		sessions.stream()
			.forEach(session -> this.deleteById(session.getId()));
	}

	public void deleteByModuleId(int id) {
		List <Session> sessions = sessionRepository.findByModuleId(id);

		sessions.stream()
			.forEach(session -> this.deleteById(session.getId()));
	}

	public void deleteBySectorId(int id) {
		List <Session> sessions = sessionRepository.findBySectorId(id);
		sessions.stream()
			.forEach(session -> this.deleteById(session.getId()));
	}
}
