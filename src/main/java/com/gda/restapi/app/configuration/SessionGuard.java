package com.gda.restapi.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.gda.restapi.app.model.Role;
import com.gda.restapi.app.repository.UserRepository;

@Component
public class SessionGuard {

	@Autowired UserRepository userRepository;
	
	public boolean IsBelongToUserId(String id) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return false;

		int intId = -1;
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {}

		var email = authentication.getName();
		var user = userRepository.findByEmail(email);
		
		return user.isPresent() && user.get().getId() == intId;
	}

	public boolean canGeneratePdf(String id) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return false;

		int intId = -1;
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {}

		var email = authentication.getName();
		var user = userRepository.findByEmail(email);
		
		return user.isPresent() && 
				( user.get().getRole().equals(Role.ADMIN.name()) 
					||
				user.get().getId() == intId);
	}
	
}
