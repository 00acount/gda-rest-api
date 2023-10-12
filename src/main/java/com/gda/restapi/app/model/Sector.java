package com.gda.restapi.app.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("SectorWithAbbr")
@Entity
public class Sector {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "the abbr field (Abbreviation) is required and must consist of at least one letter")
	private String abbr;

	@NotBlank(message = "The sector name is required and must consist of at least one letter")
	private String name;
	
	public Sector(int id) {
		this.id = id;
	}
	
}

