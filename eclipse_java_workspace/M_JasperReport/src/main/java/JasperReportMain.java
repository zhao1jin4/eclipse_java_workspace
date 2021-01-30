import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class JasperReportMain {

//	数据库没有数据preview时 默认显示为 Document is Empty,没有UI,如想有UI,点击设计窗口的工作区外,在properties窗口中的report标签->when no data type 下拉选择No Data Section
//	修改Adaper为One Empty Record 没有数据但会有UI
	public static void main(String[] args) throws Exception {
		
		Product product=new Product();
		product.setCost(BigDecimal.valueOf(20));
		product.setName("产品001");
		
		Book book=new Book();
		book.setId(BigDecimal.valueOf(20));
		book.setTitle("Java编程思想");
         
        // \J_JavaThirdLib\src\pdf_itext\simhei.ttf 导出myJasperFont.jar 放在WEB-LIB项目中了
		
		//jasperGen("H:/NEW_/eclipse_java_workspace/M_JasperReport/src/main/java/Blank_A4_product.jrxml",product);
		jasperGen("H:/NEW_/eclipse_java_workspace/M_JasperReport/src/main/java/Blank_A4_book.jrxml",book);
	}
	public static void jasperGen(String filePath,Object dbParam)throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();//jsonAPI
		String jsonProduct=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dbParam);
		//ByteArrayInputStream input=new  ByteArrayInputStream(jsonProduct.getBytes("UTF-8"));
		ByteArrayInputStream input=new  ByteArrayInputStream(jsonProduct.getBytes());
		//studio设计工具中使用的数据源，这里使用JSON
		JsonDataSource dataSource=new JsonDataSource(input);	
		//编译jrxml文件到jasper文件
		String jasperFile=JasperCompileManager.compileReportToFile(filePath);
		JasperReport report =(JasperReport)JRLoader.loadObject(new File(jasperFile));//参数可为File或FileInputStream
		
		Map<String,Object> params=new HashMap<>();
		params.put("Parameter1","参数值values1");
		JasperPrint print=JasperFillManager.fillReport(report, params,dataSource);
		
		JRXlsxExporter XlsxExporter=new JRXlsxExporter();
		JRPdfExporter pdfExporter=new JRPdfExporter();
		//genFile(print,XlsxExporter,new File("D:/tmp/excel.xlsx"));
		genFile(print,pdfExporter,new File("D:/tmp/pdf.pdf")); //生成pdf有中文问题,使用生成的myJasperFont.jar测试成功
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
