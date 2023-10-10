package com.gda.restapi.app.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.service.SectorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class SectorController {
	
	@Autowired
	private SectorService sectorService;
	
	// GET '/sectors' //
	@GetMapping({ "/admin/sectors", "/user/sectors"})
	public ResponseEntity<MappingJacksonValue> retreiveAllSector() throws JsonProcessingException {
		List<Sector> sectors = sectorService.getAll();
		
		MappingJacksonValue mappingJacksonValue = 
							sectorWithAllFieldsFilter(sectors);
		
		return ResponseEntity.ok(mappingJacksonValue); 
	}
	
	// POST '/sectors' //
	@PostMapping("/admin/sectors")
	public ResponseEntity<MappingJacksonValue> createSector(@RequestBody @Valid Sector sector) {
		Sector savedSector = sectorService.create(sector);
		
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedSector.getId()).build().toUri();
		
		MappingJacksonValue mappingJacksonValue = sectorWithAllFieldsFilter(savedSector);
		
		return ResponseEntity.created(location).body(mappingJacksonValue);
	}
	
	// GET '/sectors/{id}' //
	@GetMapping("/admin/sectors/{id}")
	public ResponseEntity<MappingJacksonValue> retrieveSector(@PathVariable String id) {
		int intId = 0;

		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the sector ID as an integer number");
		}
		
		Sector sector  = sectorService.getById(intId);
		MappingJacksonValue mappingJacksonValue = sectorWithAllFieldsFilter(sector);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}
	
	// PUT '/sectors/{id}' //
	@PutMapping("/admin/sectors/{id}")
	public ResponseEntity<MappingJacksonValue> updateSector(@PathVariable String id, @RequestBody @Valid Sector sector) {
		int intId = 0;

		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the sector ID as an integer number");
		}
		
		
		sector.setId(intId);
		Sector updatedSector = sectorService.update(sector);

		MappingJacksonValue mappingJacksonValue =
								sectorWithAllFieldsFilter(updatedSector);;

		return ResponseEntity.ok(mappingJacksonValue);
	}
	
	// DELETE '/sectors/{id}' //
	@DeleteMapping("/admin/sectors/{id}")
	public ResponseEntity<Void> deleteSector(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
			sectorService.deleteById(intId);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the sector ID as an integer number");
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
	private MappingJacksonValue sectorWithAllFieldsFilter(Object object) {
		SimpleBeanPropertyFilter filter = 
						SimpleBeanPropertyFilter.serializeAll();
		
		FilterProvider filterProvider = 
						new SimpleFilterProvider().setDefaultFilter(filter);
		
		MappingJacksonValue mappingJacksonValue = 
						new MappingJacksonValue(object);
		mappingJacksonValue.setFilters(filterProvider);
		return  mappingJacksonValue;
	}

}
