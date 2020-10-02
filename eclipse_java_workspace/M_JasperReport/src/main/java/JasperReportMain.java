import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperReportMain {

	public static void main(String[] args) throws Exception {
		Product product=new Product();
		product.setCost(BigDecimal.valueOf(20));
		product.setName("产品001");
		
		ObjectMapper mapper=new ObjectMapper();//jsonAPI
		String jsonProduct=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
		//ByteArrayInputStream input=new  ByteArrayInputStream(jsonProduct.getBytes("UTF-8"));
		ByteArrayInputStream input=new  ByteArrayInputStream(jsonProduct.getBytes());
		//studio设计工具中使用的数据源，这里使用JSON
		JsonDataSource dataSource=new JsonDataSource(input);	
		//编译jrxml文件到jasper文件
		String jasperFile=JasperCompileManager.compileReportToFile("c:/tmp/Blank_A4.jrxml");
		JasperReport report =(JasperReport)JRLoader.loadObject(new File(jasperFile));//参数可为File或FileInputStream
		
		Map<String,Object> params=new HashMap<>();
		params.put("Parameter1","参数值1");
		JasperPrint print=JasperFillManager.fillReport(report, params,dataSource);
		
		JRXlsxExporter XlsxExporter=new JRXlsxExporter();
		JRPdfExporter pdfExporter=new JRPdfExporter();
		genFile(print,XlsxExporter,new File("C:/tmp/excel.xlsx"));
		genFile(print,pdfExporter,new File("C:/tmp/pdf.pdf")); //生成pdf有中文问题
		
	}
	public static void genFile(JasperPrint jasperPrint,JRAbstractExporter exporter,File outFile) throws Exception {
		
		ByteArrayOutputStream byteOut=new ByteArrayOutputStream();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteOut));
		exporter.exportReport();
		byte[] fileArray=byteOut.toByteArray();
		FileOutputStream out=new FileOutputStream(outFile);
		out.write(fileArray);
		out.close();
	}
//	public static void genPDF(JasperPrint jasperPrint) throws Exception {
//		JRPdfExporter exporter=new JRPdfExporter();
//		ByteArrayOutputStream byteOut=new ByteArrayOutputStream();
//		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteOut));
//		exporter.exportReport();
//		byte[] fileArray=byteOut.toByteArray();
//		FileOutputStream out=new FileOutputStream(new File("C:/tmp/pdf.pdf"));
//		out.write(fileArray);
//		out.close();
//	} 
}
