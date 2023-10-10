package com.gda.restapi.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gda.restapi.app.model.User;
import com.gda.restapi.app.repository.UserRepository;

import lombok.Data;

@RestController
@RequestMapping("/api/v1")
public class AuthenticatedUserController {

	@Autowired private UserRepository userRepository;

    @GetMapping("/whos-authenticated")
    public Crendentials whosAuthenticated(Authentication authentication) {
    	Crendentials crendentials = new Crendentials();
    	if (authentication != null) {
			User user = userRepository.findByEmail(authentication.getName()).get();

			crendentials.setId(user.getId());
			crendentials.setName(user.getLastName());
			crendentials.setRole(user.getRole());
    	}
    	return crendentials;
    }

}

@Data
class Crendentials {
	private int id;
	private String name;
	private String role;
}