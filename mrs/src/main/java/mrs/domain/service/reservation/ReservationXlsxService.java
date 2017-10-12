package mrs.domain.service.reservation;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import javax.swing.text.AbstractDocument.Content;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import mrs.domain.service.reservation.xlsxForm.ReportHeader;

@Service
public class ReservationXlsxService {

	@Autowired
	protected ResourceLoader resourceLoader;

	public Path generateXlsx(final String sessionId, final String startEnd) throws Exception {
		Workbook workbook = null;
		File xlsxFile = null;
		Path xlsxFilePath = null;

		try {
			String workRootPath = System.getProperty("java.io.tmpdir") + File.separator + sessionId;
			// mkdir.
			(new File(workRootPath)).mkdir();

			// generate temporary file and path.
			xlsxFilePath = Files.createTempFile(Paths.get(workRootPath), UUID.randomUUID().toString(), "");
			xlsxFile = xlsxFilePath.toFile();

			// create POI object from template file.
			Resource resource = resourceLoader.getResource("classpath:xlsx/template001.xlsx");
			
			ReportHeader header = new ReportHeader();
			header.setIntValue(123);;
			header.setStrValue("xyz");
			header.setDecimalValue(new BigDecimal("150.123"));
			header.setDateValue(new Date());
			
			FileOutputStream fos = new FileOutputStream(xlsxFile);
			Context context = new Context();
			context.putVar("reportHeader", header);
			
			JxlsHelper.getInstance().processTemplate(resource.getInputStream(), fos, context);
			
/*			workbook = WorkbookFactory.create(resource.getInputStream());

			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue(startEnd);

			// save excel file to temporary file
			FileOutputStream fos = new FileOutputStream(xlsxFile);
			workbook.write(fos);*/

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
		return xlsxFilePath;
	}
}