package com.gda.restapi.app.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gda.restapi.app.model.Absence;

import jakarta.transaction.Transactional;

public interface AbsenceRepository extends JpaRepository<Absence, Absence.AbsenceId>{

	@Query("select a from Absence a where a.session.id = :sessionId")
	List<Absence> getAbsencesBySessionId(int sessionId);

	@Modifying
	@Transactional
	@Query("delete from Absence a where a.session.id = :sessionId")
	void deleteAbsencesBySessionId(int sessionId);

	@Modifying
	@Transactional
	@Query("delete from Absence a where a.student.id = :studentId")
	void deleteAbsencesByStudentId(int studentId);
}
