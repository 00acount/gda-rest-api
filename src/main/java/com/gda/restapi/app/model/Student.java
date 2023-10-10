package com.gda.restapi.app.model;


import java.time.LocalDate;

import com.gda.restapi.app.annotation.checkUserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "firstName is required and must consist of at least one letter")
	private String firstName;

	@NotBlank(message = "lastName is required and must consist of at least one letter")
	private String lastName;

	@NotNull(message = "apogeeCode is required")
	private int apogeeCode;

	@NotNull(message = "birthDate is required")
	private LocalDate birthDate;

	@NotNull(message = "sector is required")
	@ManyToOne
	@JoinColumn(name = "sector_id")
	private Sector sector;

	@checkUserRole(regexp = "^(S1|S2|S3|S4|S5|S6)$", message = "Semester is required")
	private String semester;

}
