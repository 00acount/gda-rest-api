package com.gda.restapi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gda.restapi.app.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query("select s from Student s where s.sector.id = :id")
	List<Student> findAllBySectorId(int id);
	
}
