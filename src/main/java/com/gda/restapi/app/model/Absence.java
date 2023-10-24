package com.gda.restapi.app.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@IdClass(Absence.AbsenceId.class)
@Entity
public class Absence {

	@Id
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@Id
	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;
	private String status;


	@Data
	public static class AbsenceId implements Serializable {

		private static final long serialVersionUID = -5446070210833006199L;
		private Student student;
		private Session session;
	}

}