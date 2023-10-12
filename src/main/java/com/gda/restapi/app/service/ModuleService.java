package com.gda.restapi.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gda.restapi.app.exception.ResourceNotFoundException;
import com.gda.restapi.app.model.Module;
import com.gda.restapi.app.model.Sector;
import com.gda.restapi.app.repository.ModuleRepository;
import com.gda.restapi.app.repository.SectorRepository;


@Service
public class ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;
	@Autowired
	private SectorRepository sectorRepository;
	
	public long getCount() {
		return moduleRepository.count();
	}

	public List<Module> getAll() {
		return moduleRepository.findAll();
	}

	public Module create(Module module) {
		List<Integer> sectorsIds = module.getSectors()
									.stream().map(sector -> sector.getId())
										.collect(Collectors.toList());

		List<Sector> sectors = sectorRepository.findAllById(sectorsIds);
		module.setSectors(sectors);
		
		return moduleRepository.save(module);
	}

	public Module getById(int id) {
		return moduleRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Module with ID " + id + " doesn't exist"));
	}

	public Module update(Module module) {
		getById(module.getId());
		
		List<Integer> sectorsIds = module.getSectors()
									.stream().map(sector -> sector.getId())
										.collect(Collectors.toList());
		List<Sector> sectors = sectorRepository.findAllById(sectorsIds);
		module.setSectors(sectors);
		
		moduleRepository.save(module);
		
		return module;
	}

	public void deleteById(int id) {
		moduleRepository.deleteById(id);
	}
}
