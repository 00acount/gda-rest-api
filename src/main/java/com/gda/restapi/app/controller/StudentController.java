package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.sectorWithAbbrFilter;

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

import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.model.Student;
import com.gda.restapi.app.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	// GET '/students' //
	@GetMapping("/students")
	public ResponseEntity<MappingJacksonValue> retreiveAllUsers() {
		List<Student> students = studentService.getAll();
		
		MappingJacksonValue mappingJacksonValue =
								sectorWithAbbrFilter(students);

		return ResponseEntity.ok(mappingJacksonValue);
	}

	// POST '/students' //
	@PostMapping("/students")
	public ResponseEntity<MappingJacksonValue> CreateStudent(@RequestBody @Valid Student student)  {
		Student savedStudent = studentService.create(student);
		
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		URI location = builder.path("/" + savedStudent.getId()).build().toUri();
		
		MappingJacksonValue mappingJacksonValue = sectorWithAbbrFilter(savedStudent);

		return ResponseEntity.created(location).body(mappingJacksonValue);

	}
	
	// GET '/students/{id}' //
	@GetMapping("/students/{id}")
	public ResponseEntity<MappingJacksonValue> retreiveUser(@PathVariable String id) {
		int intId = 0;

		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the student ID as an integer number");
		}

		Student student = studentService.getById(intId);
	
		MappingJacksonValue mappingJacksonValue =
								sectorWithAbbrFilter(student);

		return ResponseEntity.ok(mappingJacksonValue);
	}
	
	// PUT '/students/{id}' //
	@PutMapping("/students/{id}")
	public ResponseEntity<MappingJacksonValue> updateUser(@PathVariable String id, @RequestBody @Valid Student student) {
		int intId = 0;

		try {
			intId = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the student ID as an integer number");
		}
		
		
		student.setId(intId);
		Student updatedStudent = studentService.update(student);

		MappingJacksonValue mappingJacksonValue =
								sectorWithAbbrFilter(updatedStudent);;

		return ResponseEntity.ok(mappingJacksonValue);
	}
	
	
	// DELETE '/students/{id}' //
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
		int intId = 0;
		
		try {
			intId = Integer.valueOf(id);
			studentService.deleteById(intId);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("You need to specify the student ID as an integer number");
		}
		
		return ResponseEntity.noContent().build();
	}

}
