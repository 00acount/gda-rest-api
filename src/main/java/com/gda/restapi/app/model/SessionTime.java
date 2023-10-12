package com.gda.restapi.app.model;

import lombok.Getter;

@Getter
public enum SessionTime {

	FIRST_SESSSION("08H:30-10H:00"),
	SECOND_SESSSION("10H:15-11H:15"),
	THIRD_SESSSION("12H:00-13H:30"),
	FOURTH_SESSSION("13H:45-15H:15"),
	FIFTH_SESSSION("15H:30-17H:00");

	private String time;

	SessionTime(String time) {
		this.time = time;
	}
	
}
