package com.gda.restapi.app.model;

import java.time.LocalDate;

import com.gda.restapi.app.annotation.CheckUserRole;
import com.gda.restapi.app.annotation.CheckSessionTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "module_id")
	@NotNull(message = "module is required")
	private Module module;

	@ManyToOne
	@JoinColumn(name = "sector_id")
	@NotNull(message = "sector is required")
	private Sector sector;

	@CheckUserRole(regexp = "^(S1|S2|S3|S4|S5|S6)$", message = "semester field must be equal to the value among S1 and S6")
	private String semester;
	
	@CheckSessionTime(message = "the session time is incorrect")
	private String sessionTime;

	private LocalDate createdAt;
	
}
