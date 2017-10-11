package mrs.app.report;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import mrs.app.AbstractFileDownloadView;

public class ExcelDownloadView extends AbstractFileDownloadView {

	@Override
	protected InputStream getInputStream(Map<String, Object> model, HttpServletRequest request) throws IOException {

		Path filepath = (Path) model.get("filepath");
		InputStream is = new FileInputStream(filepath.toFile());
		return is;
	}

	@Override
	protected void addResponseHeader(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {

		String filename = (String) model.get("filename");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
		response.setContentType("application/vnd.ms-excel");
		
	}
}