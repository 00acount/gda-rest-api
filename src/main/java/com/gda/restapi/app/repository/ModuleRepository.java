package com.gda.restapi.app.repository;

import com.gda.restapi.app.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Integer> {

}
