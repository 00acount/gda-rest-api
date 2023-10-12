package com.gda.restapi.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gda.restapi.app.model.User;
import com.gda.restapi.app.service.ModuleService;
import com.gda.restapi.app.service.SectorService;
import com.gda.restapi.app.service.SessionService;
import com.gda.restapi.app.service.StudentService;
import com.gda.restapi.app.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
public class DashboardController {
	
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private SectorService sectorService;
	
	// GET '/dashboard' //
	@GetMapping("/dashboard")
	public MappingJacksonValue getDashboardInfo() {

		Map<String, Long> overview = new HashMap<>();
		overview.put("numberOfModules", moduleService.getCount());
		overview.put("numberOfStudents", studentService.getCount());
		overview.put("numberOfUsers", userService.getCount());
		overview.put("numberOfSessions", sessionService.getCount());
		overview.put("numberOfSectors", sectorService.getCount());

		List<User> latestUsers = userService.getLatestUsers();
 
		Map<String, Object> response = new HashMap<>();
		response.put("latestUsers", latestUsers);
		response.put("overview", overview);

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserWithoutPass", SimpleBeanPropertyFilter.serializeAllExcept("password"));
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(response);
        mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
	}
}
