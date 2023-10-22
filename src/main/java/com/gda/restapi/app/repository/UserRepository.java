package com.gda.restapi.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gda.restapi.app.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from user_app u where u.role = 'USER' order by u.lastSeen desc limit 4")
	List<User> getLatestUsers();

	@Query("select u from user_app u order by u.lastSeen desc limit 5")
	List<User> getLatestUsersAndAdmins();
	
	Optional<User> findByEmail(String email);
	
	List<User> findAllByRole(String role);

	@Query("select count(u) from user_app u where u.role = 'USER'")
	Long getUsersCount();
}
