package com.gda.restapi.app.repository;

import com.gda.restapi.app.model.Module;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

	@Query("select m from Module m join m.sectors s where s.id = :id")
	List<Module> findAllBySectorId(int id);

}
