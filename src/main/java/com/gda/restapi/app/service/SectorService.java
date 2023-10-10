package com.gda.restapi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.repository.SectorRepository;

@Service
public class SectorService {

	@Autowired
	private SectorRepository sectorRepository;
	
	public long getCount() {
		return sectorRepository.count();
	}

	public List<Sector> getAll() {
		return sectorRepository.findAll();
	}

	public Sector create(Sector sector) {
		sectorRepository.save(sector);

		return sector;
	}

	public Sector getById(int id) {
		return sectorRepository.findById(id) 
				.orElseThrow(() -> new ResourceNotFoundException("Sector with ID " + id + " doesn't exist"));
	}

	public void deleteById(int id) {
		sectorRepository.deleteById(id);
	}

	public Sector update(Sector sector) {
		getById(sector.getId());
		sectorRepository.save(sector);
		
		return sector;
	}

}
