package com.gda.restapi.app.util;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonFilterUtility {

	public static MappingJacksonValue sectorWithAbbrFilter(Object object) {
		SimpleBeanPropertyFilter filter =
				SimpleBeanPropertyFilter.serializeAllExcept("name");
		
		FilterProvider filterProvider = new SimpleFilterProvider()
												.addFilter("SectorWithAbbr", filter);

		MappingJacksonValue mappingJacksonValue = 
					new MappingJacksonValue(object);

		mappingJacksonValue.setFilters(filterProvider);

		return mappingJacksonValue;
	}

	public static MappingJacksonValue userWithoutPasswordFilter(Object object) {
		PropertyFilter filter =
				SimpleBeanPropertyFilter.serializeAllExcept("password");
		
		FilterProvider filterProvider = new SimpleFilterProvider()
												.addFilter("UserWithoutPass", filter);
		
		MappingJacksonValue mappingJacksonValue = 
					new MappingJacksonValue(object);
		
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
	public static <T> MappingJacksonValue userFilter(T object) {
		PropertyFilter sectorFilter = 
					SimpleBeanPropertyFilter.serializeAllExcept("name");

		PropertyFilter moduleFilter = 
					SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
		
		PropertyFilter userFilter = 
					SimpleBeanPropertyFilter.filterOutAllExcept("id", "lastName");
		
		FilterProvider filterProvider =
						new SimpleFilterProvider().addFilter("SectorWithAbbr", sectorFilter)
													.addFilter("UserWithoutPass", userFilter)
													.addFilter("ModuleWithName", moduleFilter);
		
		MappingJacksonValue mappingJacksonValue = 
							new MappingJacksonValue(object);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}

	public static <E> MappingJacksonValue moduleFilter(E element) {
		PropertyFilter moduleFilter = 
				SimpleBeanPropertyFilter.serializeAll();

		FilterProvider filterProvider = new SimpleFilterProvider()
				.setDefaultFilter(moduleFilter);

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(element);
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
}
