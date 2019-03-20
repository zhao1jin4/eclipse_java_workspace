package apache_poi_excel;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.collections4.collection.IndexedCollection;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcel 
{
	public static void readExcel2() throws Exception 
	{
	       
	}
	 
	public static void readExcel1() throws Exception 
	{
		   FileInputStream fin = new FileInputStream("/tmp/workbook.xlsx");
	        
	        Workbook wb = WorkbookFactory.create(fin);
	        Sheet sheet = wb.getSheetAt(0);
	        int  total=sheet.getLastRowNum();
	        Iterator<Row> i=sheet.rowIterator();
	        while(i.hasNext())
	        {
	        	//Row row = sheet.getRow(i++);
	        	Row row=i.next(); 
	        	Cell cell = row.getCell(0);  
	        	System.out.println(readCellValue(cell));  
	        	System.out.println(readCellValue( row.getCell(1)));
	        	System.out.println(readCellValue( row.getCell(2)));
	        }
	}
	public  static Object readCellValue(Cell cell)
	{
		CellType type=cell.getCellType();
		Object res=null;
		switch (type)
    	{
    		case NUMERIC :
    			res=cell.getNumericCellValue();
    			break; 
    		case  STRING:
    			res=cell.getStringCellValue();
    		case  FORMULA:
//    			if(HSSFDateUtil.isCellDateFormatted(cell))
//    				res=cell.getDateCellValue();
//    			else
//    				res=cell.getNumericCellValue();
    			res=cell.getStringCellValue();
    			System.out.println("FORMULA"); 
    			break;
    	}
		return res;
	}
	public static void writeExcel() throws Exception 
	{
		Workbook workbook = null;
		FileOutputStream out =null;
		boolean is2007=true;//true,false
		if(is2007)
		{
			/*要多加
			poi-ooxml-4.0.1.jar,
			xbean.jar  是apache项目xmlbeans的
			poi-ooxml-schemas-4.0.1.jar
			*/
			//workbook=new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
			workbook=new XSSFWorkbook();
			out=new FileOutputStream("/tmp/workbook.xlsx");
		}
		else
		{
			out = new FileOutputStream("/tmp/workbook.xls");
			workbook=new HSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "第一页");
		
		//int default_width=sheet.getColumnWidth(1);//default_width=2048
		//sheet.setColumnWidth(1, default_width*2);
		//sheet.autoSizeColumn(1,true);//没用
		
		Row row = sheet.createRow(0);
		Cell cell0 = row.createCell(0);
		cell0.setCellValue( 10000 );
		
        Cell cell1 = row.createCell(1);
        cell1.setCellValue( "中国我爱你" );

        Cell cell2 =row.createCell(2, CellType.NUMERIC);
        cell2.setCellValue(3.0);
        
		CellStyle cellStyle = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();  
		cellStyle.setDataFormat(df.getFormat("#,#0.00")); //小数点后保留两位 
		
		Font font=workbook.createFont();
		//font.setColor(Font.COLOR_RED );
		font.setColor( (short)0xc );
		font.getBold();
		cellStyle.setFont(font);
		
		cellStyle.setWrapText(true);//换行
		cell2.setCellStyle(cellStyle);
		
		XSSFRichTextString rich=new XSSFRichTextString("中华人民共和国");
		rich.applyFont(font);
		Cell cell3=row.createCell(3);
	    cell3.setCellValue(rich);
	        
		workbook.write(out);
		out.close();
	}
	public static void main(String[] args) throws Exception 
	{
		//writeExcel();
		readExcel1();
	}
}
