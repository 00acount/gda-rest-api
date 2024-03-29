package com.gda.restapi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gda.restapi.app.model.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {

	@Query("select s from Session s where s.user.id = :id")
	List<Session> findByUserId(@Param("id") int id);

	@Query("select s from Session s where s.module.id = :id")
	List<Session> findByModuleId(int id);
	@Query("select s from Session s where s.sector.id = :id")
	List<Session> findBySectorId(int id);
}
