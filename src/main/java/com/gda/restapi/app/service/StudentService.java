package com.gda.restapi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.model.Student;
import com.gda.restapi.app.repository.SectorRepository;
import com.gda.restapi.app.repository.StudentRepository;

@Service
public class StudentService {
		
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SectorRepository sectorRepository;

	public long getCount() {
		return studentRepository.count();
	}

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student create(Student student) {
		Sector sector = sectorRepository.findById(student.getSector().getId())
							.orElseThrow(() -> new ResourceNotFoundException("Sector with ID " + student.getSector().getId() + " doesn't exist"));
		
		student.setSector(sector);
		studentRepository.save(student);
		
		return student;
	}

	public Student getById(int id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " doesn't exist"));
	}

	public Student update(Student student) {
		getById(student.getId());
		Sector sector = sectorRepository.findById(student.getSector().getId())
								.orElseThrow(() -> new ResourceNotFoundException("Sector with ID " + student.getSector().getId() + " doesn't exist"));

		student.setSector(sector);
		Student savedStudent = studentRepository.save(student);
		return savedStudent;
	}

	public void deleteById(int id) {
		studentRepository.deleteById(id);
	}
	
}
