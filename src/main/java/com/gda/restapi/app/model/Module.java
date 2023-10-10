package com.gda.restapi.app.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("ModuleWithName")
@Entity
public class Module {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "module is required and must consist of at least one letter")
	private String name;
	
	@ManyToMany
	@JoinTable(
		name = "module_sector",
		joinColumns = @JoinColumn(name = "module_id"),
		inverseJoinColumns = @JoinColumn(name = "sector_id")
	)
	@NotEmpty(message = "sectors is required and must consist of at least one sector")
	private List<Sector> sectors;
	
	@ElementCollection
	@CollectionTable(name = "module_semester", joinColumns = @JoinColumn(name = "module_id"))
	@Column(name = "semester_id")
	@NotEmpty(message = "semesters is required and must consist of at least one semester")
	private List<String> semesters;

}
