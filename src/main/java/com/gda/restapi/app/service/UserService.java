package com.gda.restapi.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ConflictedResourceException;
import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Role;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private UserRepository userRepository; 
	@Autowired private SessionService sessionService;


	public Long getUsersCount() {
		return userRepository.getUsersCount();
	}	
	public List<User> getLatestUsers() {
		var latestUsers = userRepository.getLatestUsers();
		
		return latestUsers;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public List<User> getUsersByRole(Role role) {
		return userRepository.findAllByRole(role.name());
	}

	public User create(User user) {
		boolean isEmailAvailable = userRepository.findByEmail(user.getEmail()).isEmpty();
		
		if (!isEmailAvailable) 
			throw new ConflictedResourceException("This email is not available. Try using another email");

		user.setRegisteredOn(LocalDate.now());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	public User getById(int id) {
		return userRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " doesn't exist"));
	}

	public User update(User newUser) {
		User oldUser = getById(newUser.getId());

		newUser.setLastSeen(oldUser.getLastSeen());
		newUser.setRegisteredOn(oldUser.getRegisteredOn());

		if (newUser.getPassword() == "" || 
				newUser.getPassword() == null ||
				newUser.getPassword().equals("$$_#@!#@"))
			newUser.setPassword(oldUser.getPassword());
		
		userRepository.save(newUser);
		return newUser;
	}

	public void deleteById(int id) {
		sessionService.deleteByUserId(id);
		userRepository.deleteById(id);
	}

	public List<User> getLatestUsersAndAdmins() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var latestUsers = userRepository.getLatestUsersAndAdmins();
		
		if (authentication != null) {
			var email = authentication.getName();
			latestUsers
				.removeIf(usr -> usr.getEmail().equals(email));
		}

		return latestUsers;
	}

	public long getCount() {
			return userRepository.count();
	}
}
