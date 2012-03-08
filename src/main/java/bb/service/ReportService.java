package bb.service;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

	JasperReport jasperReport;
	JasperPrint jasperPrint;

	public String getReportById(String reportXml, Long id) {

		String filePath = "/tmp/reports/simple_report.pdf";

		try {
			jasperReport = JasperCompileManager.compileReport(reportXml);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					new HashMap(), new JREmptyDataSource());

			JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return filePath;
	}

}
