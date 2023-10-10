package com.gda.restapi.app.util;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.gda.restapi.app.model.Absence;
import com.gda.restapi.app.model.Session;
import com.gda.restapi.app.model.Student;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class GeneratePDF {
	private final Color BORDER_COLOR = new Color(80, 80, 80);
	private final String font = FontFactory.HELVETICA;
	private final Document document;

	public GeneratePDF(Document document) {
		this.document = document;
	}
	
	
public void sessionInfo(Session session) {
	
		String moduleName = session.getModule().getName(); 
		String sessionTime = session.getSessionTime().replace("-", "\n");
		String creationTime = session.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String professorName = session.getUser().getLastName();
		String sectorName = session.getSector().getAbbr();
		String semesterName = session.getSemester();
	
		
		PdfPTable  table = new PdfPTable(5);
		table.setWidthPercentage(100);
		
		PdfPCell module = createSessionInfoCell("Module", Font.BOLDITALIC, 2, 0);
		PdfPCell moduleValue = createSessionInfoCell(moduleName, Font.NORMAL, 3, 0);

		PdfPCell _session = createSessionInfoCell("Session", Font.BOLDITALIC, 1, Element.ALIGN_CENTER);
		PdfPCell _sessionValue = createSessionInfoCell(sessionTime, Font.NORMAL, 1, Element.ALIGN_CENTER);

		PdfPCell creation = createSessionInfoCell("Date", Font.BOLDITALIC, 1, Element.ALIGN_CENTER);
		PdfPCell creationValue = createSessionInfoCell(creationTime, Font.NORMAL, 1, Element.ALIGN_CENTER);
		
		PdfPCell professor = createSessionInfoCell("Professor", Font.BOLDITALIC, 1, Element.ALIGN_CENTER);
		PdfPCell professorValue = createSessionInfoCell(professorName, Font.NORMAL, 1, Element.ALIGN_CENTER);

		PdfPCell sector = createSessionInfoCell("Sector", Font.BOLDITALIC, 1, Element.ALIGN_CENTER);
		PdfPCell sectorValue = createSessionInfoCell(sectorName, Font.NORMAL, 1, Element.ALIGN_CENTER);

		PdfPCell semester = createSessionInfoCell("Semester", Font.BOLDITALIC, 1, Element.ALIGN_CENTER);
		PdfPCell semesterValue = createSessionInfoCell(semesterName, Font.NORMAL, 1, Element.ALIGN_CENTER);
		
		table.addCell(module);
		table.addCell(moduleValue);
		

		table.addCell(_session);
		table.addCell(creation);
		table.addCell(professor);
		table.addCell(sector);
		table.addCell(semester);
		
		table.addCell(_sessionValue);
		table.addCell(creationValue);
		table.addCell(professorValue);
		table.addCell(sectorValue);
		table.addCell(semesterValue);
		

        document.add(table);
        document.add(new Paragraph("\n"));
	}
	
	public void addTableTitle(String title) {
		PdfPTable  table = new PdfPTable(1);
		table.setWidthPercentage(100);

		Font black = FontFactory.getFont(font, 25, Font.UNDERLINE, Color.BLACK);
		black.setStyle(Font.ITALIC | Font.UNDERLINE);

		Paragraph paragraph  = new Paragraph(new Chunk(title,  black));

		PdfPCell cell = new PdfPCell(paragraph);
		cell.setPadding(25);
		cell.setHorizontalAlignment(1);
		cell.setBorderColor(Color.WHITE);
		table.addCell(cell);

		
        document.add(table);
	}
	
	public void createTableHeader(String ...args) {
		PdfPTable  table = new PdfPTable(4);
		table.setWidthPercentage(100);
		Arrays.stream(args).forEach(columnHead -> {
            Font white = FontFactory.getFont(font, Font.DEFAULTSIZE, Font.BOLD, Color.BLACK);

			Paragraph paragraph = new Paragraph(new Chunk(columnHead, white));
			PdfPCell cell = new PdfPCell(paragraph);

			cell.setBorderColor(BORDER_COLOR);
			cell.setBorderWidth(1.5f);
			cell.setHorizontalAlignment(1);
			cell.setPadding(12);
			
			table.addCell(cell);
		});
        document.add(table);
	}

	public void createTableBody(List<Absence> absenceList) {
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		absenceList.forEach(absence -> {
				Student student = absence.getStudent();
				String status = absence.getStatus();
				
				PdfPCell cell1 = createCell(student.getFirstName());
				PdfPCell cell2 = createCell(student.getLastName());
				PdfPCell cell3 = createCell(String.valueOf(student.getApogeeCode()));
				PdfPCell cell4 = createStatusCell(status);				

				table.addCell(cell1);
				table.addCell(cell2);
				table.addCell(cell3);
				table.addCell(cell4);
		});
        document.add(table);
	}
	
	private PdfPCell createCell(String para) {
		Font black = FontFactory.getFont(font, Font.DEFAULTSIZE, Font.ITALIC, Color.BLACK);
		Paragraph paragraph  = new Paragraph(new Chunk(para,  black));
		PdfPCell cell = new PdfPCell(paragraph);
		
		cell.setBorderColor(BORDER_COLOR);
		cell.setHorizontalAlignment(1);
		cell.setBorderWidth(1.5f);
		cell.setPadding(12);
		
		return cell;
	}
	
	private PdfPCell createStatusCell(String field) {
		String status = field.equals("ABSENT")? "4": " ";
		Font black = FontFactory.getFont(FontFactory.ZAPFDINGBATS, Font.DEFAULTSIZE, Font.BOLDITALIC, Color.BLACK);
		Paragraph paragraph  = new Paragraph(new Chunk(status,  black));
		PdfPCell cell = new PdfPCell(paragraph);

		cell.setBorderColor(BORDER_COLOR);
		cell.setHorizontalAlignment(1);
		cell.setBorderWidth(1.5f);
		cell.setPadding(12);
		return cell;
	}
	
	private PdfPCell createSessionInfoCell(String phrase, int fontStyle, int colspan, int horizontalAlignment) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, fontStyle, Color.BLACK);
		Paragraph paragraph = new Paragraph(phrase, font);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setBorderColor(BORDER_COLOR);
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(12);
		cell.setBorderWidth(1.5f);
		cell.setColspan(colspan);
		return cell;
	}
}
