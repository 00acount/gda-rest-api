package com.gda.restapi.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository; 


	public long getCount() {
		return userRepository.count();
	}
	
	public List<User> getLatestUsers() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var latestUsers = userRepository.getLatestUsers();
		
		if (authentication != null) {
			var email = authentication.getName();
			latestUsers
				.removeIf(usr -> usr.getEmail().equals(email));
		}
		return latestUsers;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User create(User user) {
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
		userRepository.deleteById(id);
	}
}
