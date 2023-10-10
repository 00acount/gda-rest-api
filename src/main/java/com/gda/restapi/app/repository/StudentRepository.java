package com.gda.restapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gda.restapi.app.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
}
