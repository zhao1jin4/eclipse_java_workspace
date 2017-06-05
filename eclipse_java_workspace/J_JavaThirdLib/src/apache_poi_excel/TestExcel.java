package apache_poi_excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcel 
{
	public static void readExcel2() throws Exception 
	{
	       
	}
	 
	public static void readExcel1() throws Exception 
	{
		  FileInputStream fin = new FileInputStream("c:/temp/workbook.xlsx");
	        
	        Workbook wb = WorkbookFactory.create(fin);
	        Sheet sheet = wb.getSheetAt(0);
	        Iterator<Row> i=sheet.rowIterator();
	        while(i.hasNext())
	        {
	        	//Row row = sheet.getRow(i++);
	        	Row row=i.next();
	        	Cell cell = row.getCell(0);
	        	double num=cell.getNumericCellValue();
	        	Cell cell1 = row.getCell(1);
	        	String  str=cell1.getStringCellValue();
	        	System.out.println(num+":"+str);
	        	
	        }
	}
	public static void writeExcel() throws Exception 
	{
		Workbook webbook = null;
		FileOutputStream out =null;
		boolean is2007=true;//true,false
		if(is2007)
		{
			/*要多加
			poi-ooxml-3.8.x.jar,
			xbean.jar  是apache项目xmlbeans的
			poi-ooxml-schemas-3.8-x.jar
			*/
			//webbook=new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
			webbook=new XSSFWorkbook();
			out=new FileOutputStream("c:/temp/workbook.xlsx");
		}
		else
		{
			out = new FileOutputStream("c:/temp/workbook.xls");
			webbook=new HSSFWorkbook();
		}
		Sheet sheet = webbook.createSheet();
		webbook.setSheetName(0, "我的第一个Sheet");
		
		int default_width=sheet.getColumnWidth(1);//default_width=2048
		sheet.setColumnWidth(1, default_width*2);
		
		Row row = sheet.createRow(0);
		Cell cell0 = row.createCell(0);
		cell0.setCellValue( 10000 );
        Cell cell1 = row.createCell(1);
        cell1.setCellValue( "中国我爱你" );
        webbook.write(out);
		out.close();
	}
	public static void main(String[] args) throws Exception 
	{
		writeExcel();
		//readExcel1();
	}
}
