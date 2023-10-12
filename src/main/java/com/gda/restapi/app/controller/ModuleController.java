package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.moduleFilter;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.model.Module;
import com.gda.restapi.app.service.ModuleService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1")
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	
	// GET '/modules' //
	@GetMapping({ "/admin/modules", "/user/modules" })
	public ResponseEntity<MappingJacksonValue> retrieveAllModules() {
		List<Module> modules = moduleService.getAll();

		MappingJacksonValue mappingJacksonValue = moduleFilter(modules);

		return ResponseEntity.ok(mappingJacksonValue);
	}


	// POST '/modules' //
	@PostMapping("/admin/modules")
	public ResponseEntity<MappingJacksonValue> createModule(@RequestBody @Valid Module module) {
		Module savedModule = moduleService.create(module);
		
		UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedModule.getId()).build().toUri();
		
		

		MappingJacksonValue mappingJacksonValue = moduleFilter(savedModule);

		return ResponseEntity.created(location).body(mappingJacksonValue);
	}


	// GET '/modules/{id}' //
	@GetMapping("/admin/modules/{id}")
	public ResponseEntity<MappingJacksonValue> retrieveModule(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the module ID as an integer number");
		}

		Module module = moduleService.getById(intId);

		MappingJacksonValue mappingJacksonValue = moduleFilter(module);
		
		return ResponseEntity.ok(mappingJacksonValue);
	}

	// PUT '/modules/{id}' //
	@PutMapping("/admin/modules/{id}")
	public ResponseEntity<MappingJacksonValue> updateModule(@PathVariable String id, @RequestBody @Valid Module module) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the module ID as an integer number");
		}
		
		module.setId(intId);
		Module updatedModule = moduleService.update(module);

		MappingJacksonValue mappingJacksonValue = moduleFilter(updatedModule);

		return ResponseEntity.ok(mappingJacksonValue);
	}

	// DELETE '/modules/{id}' //
	@DeleteMapping("/admin/modules/{id}")
	public ResponseEntity<Void> deleteModule(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
			moduleService.deleteById(intId);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the module ID as an integer number");
		}
		
		return ResponseEntity.noContent().build();
	}
	
	
}