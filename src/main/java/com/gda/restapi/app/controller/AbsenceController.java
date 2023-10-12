package com.gda.restapi.app.controller;

import static com.gda.restapi.app.util.JsonFilterUtility.userFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gda.restapi.app.exception.InvalidIdFormatException;
import com.gda.restapi.app.model.Absence;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.service.AbsenceService;
import com.gda.restapi.app.service.SessionService;
import com.gda.restapi.app.util.GeneratePDF;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/v1")
public class AbsenceController {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private AbsenceService absenceService;
	
	
	//	GET '/users/{id}/sessions/{sid}/absence' //
	@GetMapping("/user/users/{id}/sessions/{sid}/absence")
	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<MappingJacksonValue> retrieveUserSession(@PathVariable String id, @PathVariable String sid) {
		int userId = 0, sessionId = 0;
		try {
			userId = Integer.valueOf(id);
			sessionId = Integer.valueOf(sid);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("Both the session ID or the user ID must be an integer");
		}

		List<Absence> absences = absenceService.getAbsencesSessionForUser(userId, sessionId);
		MappingJacksonValue mappingJacksonValue = userFilter(absences);

		return ResponseEntity.ok(mappingJacksonValue);
	}

	//	PUT '/users/{id}/sessions/{sid}/absence' //
	@PutMapping("/user/users/{id}/sessions/{sid}/absence")
	@PreAuthorize("@sessionGuard.IsBelongToUserId(#id)")
	public ResponseEntity<MappingJacksonValue> updateUserSession(@PathVariable String id, @PathVariable String sid, @RequestBody List<Absence> absences) {
		int userId = 0, sessionId = 0;
		try {
			userId = Integer.valueOf(id);
			sessionId = Integer.valueOf(sid);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("Both the session ID or the user ID must be an integer");
		}

		List<Absence> updatedAbsences = absenceService.UpdateAbsenceStatusForSession(userId, sessionId, absences);
		MappingJacksonValue mappingJacksonValue = userFilter(updatedAbsences);

		return ResponseEntity.ok(mappingJacksonValue);
	}

	//	GET '/users/{id}/sessions/{sid}/absence/generatePDF' //
	@GetMapping({ "user/users/{id}/sessions/{sid}/absence/generatePDF", 
			"admin/users/{id}/sessions/{sid}/absence/generatePDF" })
	@PreAuthorize("@sessionGuard.canGeneratePdf(#id)")
	public void generatePdf(@PathVariable String id, @PathVariable String sid, HttpServletResponse response) throws IOException {
		int userId = 0, sessionId = 0;
		try {
			userId = Integer.valueOf(id);
			sessionId = Integer.valueOf(sid);
		} catch (NumberFormatException e) {
			throw new InvalidIdFormatException("Both the session ID or the user ID must be an integer");
		}

		List<Absence> absenceList = absenceService.getAbsencesSessionForUser(userId, sessionId);

		Session session = sessionService.getById(userId, sessionId);

		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=my.pdf");

			Document document = new Document(PageSize.A4);

			OutputStream os = response.getOutputStream();

			PdfWriter.getInstance(document, os);
			
			// Open the document for writing
			document.open();

			// Add content to the PDF
			GeneratePDF generatePDF = new GeneratePDF(document);

			generatePDF.addTableTitle("Absence List");
			generatePDF.sessionInfo(session);
			generatePDF.createTableHeader("First Name", "Last Name", "Apogee Code", "Is Absent?");
			generatePDF.createTableBody(absenceList);
			

			// Close the document
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
