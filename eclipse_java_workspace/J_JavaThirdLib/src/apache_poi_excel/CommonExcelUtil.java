package apache_poi_excel;
 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonExcelUtil 
{ 
	/**
	 * ����excel
	 * @param list Ҫʹ�õ�����
	 * @param workbookOutputstream �����excel���������ⲿ����򿪹ر�
	 * @param titles ����
	 * @param fiels �������� �ͱ����Ӧ˳��
	 * @throws Exception
	 */
	public static void exportObject2Excel(List<? extends Object> list,OutputStream workbookOutputstream,String [] titles,String [] fiels)throws Exception 
	{
		if(titles==null|| titles.length==0 || fiels==null || fiels.length==0|| fiels.length!=titles.length)
			throw new Exception("��������fields��titles����ǿյĲ���С��ͬ");
		Workbook workbook=new XSSFWorkbook(); 
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "��һҳ");
		
		CellStyle dateCellStyle=workbook.createCellStyle(); 
		short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
		dateCellStyle.setDataFormat(shortDateFormat);
		
		CellStyle centerStyle=workbook.createCellStyle(); 
		centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		centerStyle.setAlignment(HorizontalAlignment.CENTER);
//		centerStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		for(int i=0;i<titles.length;i++)//�п�
		{
//			sheet.autoSizeColumn(i,true);//��Ч
			int default_width=sheet.getColumnWidth(i);//default_width=2048
			sheet.setColumnWidth(i, default_width*2);
		}
		Row titleRow = sheet.createRow(0);
		for(int i=0;i<titles.length;i++)//д����
		{
			Cell cell0 = titleRow.createCell(i);
			cell0.setCellValue(titles[i] );
			cell0.setCellStyle(centerStyle);
		}
		int colNum=1;//��������
		for(Object stu:list)
		{
			Row row = sheet.createRow(colNum++);
			for(int f=0;f<fiels.length;f++)
			{
				Object obj=getObjectField(stu,fiels[f] );//����
				if(obj instanceof Integer || obj instanceof Long)
				{
					row.createCell(f,CellType.NUMERIC).setCellValue(Long.parseLong(obj.toString()));
				}else if(obj instanceof Float ||obj instanceof Double )
				{
					Cell cell = row.createCell(f,CellType.NUMERIC);
					cell.setCellValue(Double.parseDouble(obj.toString()));
					CellStyle cellStyle = workbook.createCellStyle();
					DataFormat df = workbook.createDataFormat();  
					cellStyle.setDataFormat(df.getFormat("#,#0.00")); //С���������λ 
				
					Font font=workbook.createFont();
					//font.setColor(Font.COLOR_RED );
					font.setColor( (short)0xc );
					font.getBold();
					cellStyle.setFont(font);
					cellStyle.setWrapText(true);//����
					
					cell.setCellStyle(cellStyle);
				}
				else if(obj instanceof Date)
				{
					Cell cell = row.createCell(f);
					
//					String strDate=new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);//yyyy-MM-dd HH:mm:ss
//					cell.setCellValue(strDate);
					//����Ϊ���ڸ�ʽ 
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(dateCellStyle);
					cell.setCellValue((Date)obj);
				}else if(obj instanceof String)
					row.createCell(f).setCellValue((String)obj);
//				XSSFRichTextString rich=new XSSFRichTextString("�л����񹲺͹�");
//				rich.applyFont(font);
			}
		} 
		workbook.write(workbookOutputstream); 
	}
	/**
	 * excel���빦�� 
	 * @param workbookInput excelת����,�ⲿ����򿪺͹ر�
	 * @param clazz ������
	 * @param fiels ��������ֶ�˳��
	 * @return ת������ļ���
	 * @throws Exception
	 */
	public  static <T> List<T> importExcel2Object(InputStream workbookInput,Class<T> clazz,String [] fiels)throws Exception 
	{
		List<T> res=new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(workbookInput);
        Sheet sheet = workbook.getSheetAt(0);
        int  total=sheet.getLastRowNum();
//      Iterator<Row> i=sheet.rowIterator();
//      while(i.hasNext())
//        	Row row=i.next(); 
        for(int i=1;i<total;i++)//��������
        {
        	Row row = sheet.getRow(i);
        	//Object obj=new Student ();//ͨ�û�
        	T obj=clazz.getDeclaredConstructor().newInstance();
        	for(int f=0;f<fiels.length;f++)
			{	
        		Cell cell = row.getCell(f);  
        		if(cell!=null)
        		{
        			Object value= readCellValue(cell);
    				setObjectField(obj,fiels[f],value );//����
        		}
			}
        	res.add(obj); 
        }
		return res;
	}
	public  static Object readCellValue(Cell cell)
	{
		CellType type=cell.getCellType(); 
		Object res=null;
		 
		switch (type)
    	{
    		case NUMERIC :
    			/*
    			 	CellStyle dateCellStyle=workbook.createCellStyle(); 
    				short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
    				dateCellStyle.setDataFormat(shortDateFormat); 
    				cell.setCellStyle(dateCellStyle);
    			 */
    			if (HSSFDateUtil.isCellDateFormatted(cell)) {//�����ڸ�ʽ
    				res=cell.getDateCellValue(); 
    				break; 
    	        }  
    			 double dbl=cell.getNumericCellValue();//��excel��int ����double
    			 int num=(int)dbl;
    			 if(dbl-num>0)
    				 res=dbl;
    			 else
    				 res=num;
//     			NumberFormat format=  NumberFormat.getInstance();
//     			res=format.format(value);//ת��Ϊ�ִ�
    			break; 
    		case  STRING:
    			res=cell.getStringCellValue();
    			break;
    		case  FORMULA: // ��ʽ 
    			//res=  cell.getCellFormula(); 
    			res=cell.getStringCellValue();
    			break;
			default:
				res=cell.getStringCellValue();
    	}
		return res;
	}
	//�����������ֵ
	private static Object getObjectField(Object obj,String strField)throws Exception 
	{
		if(obj instanceof Map)
		{
			Map map=(Map)obj;
			return map.get(strField);
		}
		Field  field=obj.getClass().getDeclaredField(strField);
		if(!field.isAccessible() )//JDK9 canAccess(obj),JDK8 isAccessible()
			field.setAccessible(true);
		Object res=field.get(obj); 
		return  res;
	}
	//��������ֵ
	private static void setObjectField(Object obj,String strField,Object value)throws Exception 
	{
		if(value==null)
			return ;
		if(obj instanceof Map)
		{
			Map map=(Map)obj;
			map.put(strField,value);
			return ;
		}
		
		Field  field=obj.getClass().getDeclaredField(strField);
		if(!field.isAccessible())//JDK9 canAccess,JDK8 isAccessible()
			field.setAccessible(true);
		if(field.getType() == Timestamp.class &&   value instanceof Date)
		{
			value=new Timestamp(((Date)value).getTime());
		}
		if(field.getType() == Long.class &&  value instanceof Integer)
		{
			value=Long.parseLong(value.toString());
		}
		if( (field.getType()  == Long.class || field.getType()  == long.class )
			&&  value instanceof Double)//��������excel����ʾΪ��ѧ��������Double
		{
			String longStr=new DecimalFormat("#").format((Double)value);
			value=Long.parseLong(longStr);
		}
		if(field.getType()  == String.class && value!=null && value.getClass()!=  String.class)
			//Java��String����excel�е��ִ�1�Ҷ�����ʾΪ����
		{
			value=value.toString();
		}
	
//		if(field.getType() == Date.class)
//		{
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//"yyyy-MM-dd HH:mm:ss"
//			Date date=format.parse(value.toString());
//			field.set(obj,date); 
//		}else
			field.set(obj,value); 
	}
	
	
	
	private  static List<Student> queryDBData() //ģ�����ݿ�����
	{
		List<Student> list=new ArrayList<>();
		for(int i=0;i<10;i++)
		{
			Student stu=new Student();
			stu.setBirthday(new Date());
			stu.setId(12345678901234L+i);
			stu.setWeight(20000000+i);
			stu.setName("�ܳ��ܳ�������"+i);
			list.add(stu);
		}
		return list;
	}
	private  static List<Map<String,Object>> queryMapDBData() //ģ�����ݿ�����
	{
		List<Map<String,Object>> list=new ArrayList<>();
		for(int i=0;i<10;i++)
		{
			Map<String,Object> stu=new HashMap();
			stu.put("birthday",new Date());
			stu.put("id",45678901234L+i);
			stu.put("weight",20000000+i);
			stu.put("name","�ܳ��ܳ�������"+i);
			list.add(stu);
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception 
	{
//		List<Student> list=queryDBData();
		List<Map<String,Object>> list=queryMapDBData();
//		
		OutputStream workbookOutput=new FileOutputStream("/tmp/workbook.xlsx");//ServletOutputStream
		String [] titles=new String[] {"���","����","����","��������"};
		String [] fields=new String[] {"id","name","weight","birthday"};//˳��
		exportObject2Excel(list,workbookOutput,titles,fields);
		workbookOutput.close();
		System.out.println("�������");
		
		InputStream workbookInputStream=new FileInputStream("/tmp/workbook.xlsx"); 
		List<Student> bookList=importExcel2Object(workbookInputStream,Student.class,fields);
		workbookInputStream.close();
		System.out.println("�������");
		System.out.println(bookList);
		
	}
}

class Student
{
	long id;
	String name;
	double weight;
	Date birthday;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Student {id=" + id + ", name=" + name + ", weight=" + weight + ", birthday=" + birthday + "}";
	} 
	
}
