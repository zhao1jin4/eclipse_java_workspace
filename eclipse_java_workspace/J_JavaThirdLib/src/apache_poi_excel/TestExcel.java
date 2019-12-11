package apache_poi_excel;
  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcel 
{
	 
	public static void readExcel() throws Exception 
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
	        	
	        	System.out.println(CommonExcelUtil.readCellValue(cell));  
	        	System.out.println(CommonExcelUtil.readCellValue( row.getCell(1)));
	        	System.out.println(CommonExcelUtil.readCellValue( row.getCell(2)));
	        	System.out.println(CommonExcelUtil.readCellValue( row.getCell(3)));
	        	System.out.println(CommonExcelUtil.readCellValue( row.getCell(4)));
	        }
	}
	
	public static void writeExcel() throws Exception 
	{
		Workbook workbook = null;
		FileOutputStream out =null;
		boolean is2007=true;//true,false
		if(is2007)
		{
			/*Ҫ���
			poi-ooxml-4.0.1.jar,
			xbean.jar  ��apache��Ŀxmlbeans��
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
		workbook.setSheetName(0, "��һҳ");

		sheet.createFreezePane(0, 1);//�����һ�У��������
		
		int default_width=sheet.getColumnWidth(1);//default_width=2048
		sheet.setColumnWidth(1, default_width*2);
		//sheet.autoSizeColumn(1,true);//û��
		
		CellStyle baseCellStyle = workbook.createCellStyle();  
		Font font=workbook.createFont();
		//font.setColor(Font.COLOR_RED );
		font.setColor( IndexedColors.BLACK.getIndex());//������ɫ (short)0xc 
		//font.getBold(); 
		
		//������ʽ
		baseCellStyle.setFont(font); 
		baseCellStyle.setBorderTop(BorderStyle.THIN);
		baseCellStyle.setBorderBottom(BorderStyle.THIN);
		baseCellStyle.setBorderLeft(BorderStyle.THIN);
		baseCellStyle.setBorderRight(BorderStyle.THIN);
		baseCellStyle.setAlignment(HorizontalAlignment.CENTER);

		baseCellStyle.setWrapText(true);//���� 
	 
	    
		CellStyle numberCellStyle=workbook.createCellStyle(); 
		numberCellStyle.cloneStyleFrom(baseCellStyle);
		DataFormat df = workbook.createDataFormat();  
		numberCellStyle.setDataFormat(df.getFormat("#,#0.00")); //С���������λ 
		
		//����ɫ��ʽ
		 CellStyle colorCellStyle=workbook.createCellStyle(); 
		 colorCellStyle.cloneStyleFrom(baseCellStyle);
		 colorCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		 colorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		
		//��ɫ��ʽ
		 CellStyle redColorCellStyle=workbook.createCellStyle(); 
		 redColorCellStyle.cloneStyleFrom(baseCellStyle);
		 redColorCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		 redColorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		  
		//������ʽ
	    CellStyle dateCellStyle=workbook.createCellStyle(); 
	    dateCellStyle.cloneStyleFrom(baseCellStyle);
		short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
		dateCellStyle.setDataFormat(shortDateFormat);  
		
		 
		//��ɫ������ʽ
		Font redFont=workbook.createFont(); 
		redFont.setColor( IndexedColors.RED.getIndex()); 
		
		Row row = sheet.createRow(0);//��Ҫд�ڶ��У���Ҫʹ��sheet.getRow(0)��,�����createRow�ͻ�ѵ�һ�е���������
		Cell cell0 = row.createCell(0);
		cell0.setCellValue( 10000 );
		cell0.setCellType(CellType.NUMERIC);
		cell0.setCellStyle(baseCellStyle);
	 
        Cell cell1 = row.createCell(1);
        cell1.setCellValue( "�й��Ұ���" );
        cell1.setCellStyle(colorCellStyle);
        
        
        Cell cell2 =row.createCell(2, CellType.NUMERIC);
        cell2.setCellValue(3.0);  
		cell2.setCellStyle(baseCellStyle);
		
		XSSFRichTextString rich=new XSSFRichTextString("�л����񹲺͹�");
		rich.applyFont(0, 3, redFont);
		//rich.applyFont(redFont);
		Cell cell3=row.createCell(3);
	    cell3.setCellValue(rich);  
	    
	    Cell cell4=row.createCell(4); 
		cell4.setCellStyle(dateCellStyle);
		cell4.setCellValue(new Date());
		 
		workbook.write(out);
		out.close();
	} 
	public static void wirteContraintData() throws Exception
	{
		Workbook 	workbook=new XSSFWorkbook();
		FileOutputStream out=new FileOutputStream("/tmp/workbook.xlsx"); 
		Sheet sheet = workbook.createSheet();
		
		sheet.protectSheet("");//���ɱ༭1 ����������������
		//�籣�������ӣ�ɾ���У�������

		CellStyle baseCellStyle = workbook.createCellStyle();  
		baseCellStyle.setLocked(false);//���ɱ༭2 Ĭ��ֵΪtrue,�������������Ч
		//��Ԫ��������ʽ
	    CellStyle lockCellStyle=workbook.createCellStyle(); 
	    lockCellStyle.cloneStyleFrom(baseCellStyle); 
	    lockCellStyle.setLocked(true);//ֻ����Ԫ�� 
	    
	    int noEditCol=2;
		sheet.setDefaultColumnStyle(noEditCol, lockCellStyle);//���ɱ༭3,�м���
		 
		
		 DataValidationHelper help = new XSSFDataValidationHelper((XSSFSheet)sheet);
		 int maxRows=1048576 -1 ;//excel 2010 � 1048576 -1 =0xFFFFF
		//int maxRows=0xFFFFF;
		 {//�����б�
				int columnNum=0;
				XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) help.createExplicitListConstraint(new String[] {"δ֪","��","Ů"});
//				XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(new String[] {"δ֪","��","Ů"});
				CellRangeAddressList regions=new CellRangeAddressList(1,maxRows,columnNum,columnNum);//(int firstRow, int lastRow, int firstCol, int lastCol)
				DataValidation validation = help.createValidation(constraint, regions);
				 
				//�����벻�Ϸ�����������
			    validation.setEmptyCellAllowed(true); //��Ϊ��
		        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);  
		        validation.setShowPromptBox(true);
				validation.createErrorBox("Error", "������ѡ��ֵ");
			    validation.setShowErrorBox(true);
//		      validation.setSuppressDropDownArrow(true);
		        
				sheet.addValidationData(validation);  
		 }
		 {
			 //���ַ�Χ 
			 int columnNum=1; 
//	        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) help.createNumericConstraint( DVConstraint.ValidationType.INTEGER, DVConstraint.OperatorType.BETWEEN, "10", "100" ) ; 
	        XSSFDataValidationConstraint dvConstraint= new XSSFDataValidationConstraint(DVConstraint.ValidationType.DECIMAL, DVConstraint.OperatorType.BETWEEN, "0.00", "100.00");
			CellRangeAddressList addressList = new CellRangeAddressList(1, maxRows, columnNum, columnNum);
			XSSFDataValidation validation = (XSSFDataValidation) help.createValidation(dvConstraint, addressList);

			validation.setEmptyCellAllowed(true); //��Ϊ��
			validation.setErrorStyle(DataValidation.ErrorStyle.STOP);  
			validation.setShowPromptBox(true);
			validation.createErrorBox("Error", "С����Χ��0-100");
			validation.setShowErrorBox(true);  
//			validation.setSuppressDropDownArrow(true);
			
			sheet.addValidationData(validation);  
		 }
		
		{//����
			Row row = sheet.createRow(0); 
			Cell sexTitle = row.createCell(0);
			sexTitle.setCellValue( "�ձ�" );  
			
			Cell sexAge = row.createCell(1);
			sexAge.setCellValue( "����" ); 
			
			Cell noEdit = row.createCell(2);
			noEdit.setCellValue( "���ɱ༭��" );  
			
		}
		{//����
			//��������
			Row row1 = sheet.createRow(1); 
			Cell sexVal = row1.createCell(0);
			sexVal.setCellValue( "��" );
			sexVal.setCellStyle(baseCellStyle);
			
			Cell ageVal = row1.createCell(1);
			ageVal.setCellValue( 20 );
			ageVal.setCellStyle(baseCellStyle);
			
			
			Cell noEditVal = row1.createCell(2);
			noEditVal.setCellValue( "�����޸�ֵ" );
			noEditVal.setCellStyle(lockCellStyle);//���ɱ༭3,��Ԫ�񼶱�
			//û��createCell�е�Ҳ�ǲ��ɱ༭��
			
			//�쳣����
			Row row2 = sheet.createRow(2); 
			sexVal = row2.createCell(0);
			sexVal.setCellValue( "x" );//���ǿ���д���
			sexVal.setCellStyle(baseCellStyle);
			
			ageVal = row2.createCell(1);
			ageVal.setCellValue( -10 );//���ǿ���д���
//			ageVal.setCellStyle(baseCellStyle);
			ageVal.setCellType(CellType.STRING);//�޸ĵ�Ԫ�����ݺ󣬻��ǻ�����ݣ����ֲ��ᣬӰ�����ɵ�excel�ĵ�Ԫ��ʽ��
			
			CellStyle numberStringStyle = workbook.createCellStyle(); 
			DataFormat  format = workbook.createDataFormat();
			numberStringStyle.setDataFormat(format.getFormat("@"));
			ageVal.setCellStyle(numberStringStyle);//���ɵ�excel�ĵ�Ԫ��ʽ���ı���ʽ
		} 
		workbook.write(out);
		out.close();
	}
	public static void main(String[] args) throws Exception 
	{
//		writeExcel();
//		readExcel(); 
		wirteContraintData();
		
	}
}
 
