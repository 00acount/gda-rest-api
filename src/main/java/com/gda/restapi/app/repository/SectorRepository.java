package com.gda.restapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gda.restapi.app.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Integer> {

}
