package spring_fdfs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFastDFS
{
	public static void main(String[] args) throws Exception
	{
		byte[] byteArray=getExcelArray();
		  ApplicationContext ctx=new ClassPathXmlApplicationContext("spring_fdfs/spring-dfs.xml");
				
		  FileModel fileModel = new FileModel();
			fileModel.setContentType("application/vnd.ms-excel;charset=utf-8");
			fileModel.setData(byteArray);
			fileModel.setFileName("excel数据.xls");
			fileModel.setExtName("xls");
			fileModel.setSize((long) byteArray.length);
			Date now=Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
			fileModel.setUploadDate(now);
			
			
		 FileSystemClient client= ctx.getBean("fileStorage",FileSystemClient.class);
		String filePath= client.upload(fileModel);
	     System.out.println(filePath);
	}
	
	
	public static byte[] getExcelArray() throws Exception  
	{
		 int rows=0;
		 int columns=0;
		 XSSFWorkbook	webbook=new XSSFWorkbook();
		Sheet sheet = webbook.createSheet();
		webbook.setSheetName(0, "第一页");
		int default_width=sheet.getColumnWidth(columns);
		sheet.setColumnWidth(columns, default_width*2);
		
		for(int i=0;i<100;i++)
		{
			
			Row row = null;
			if(columns==0)
				row = sheet.createRow(rows);
			else
				row = sheet.getRow(rows);
			Cell cell = row.createCell(columns);
			cell.setCellValue("数值"+i);
			if(rows<50-1) //excel 每列50行
			{
				rows++;
			}else
			{
			 	rows=0;
				columns++;
				sheet.setColumnWidth(columns, default_width*2);
			}
		}
		ByteArrayOutputStream outPut = new ByteArrayOutputStream();
		webbook.write(outPut);
		byte[] byteArray = outPut.toByteArray();
		return byteArray;
			
	}
}
