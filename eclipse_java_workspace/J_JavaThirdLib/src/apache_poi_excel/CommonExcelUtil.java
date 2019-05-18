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
	 * 导出excel
	 * @param list 要使用的数据
	 * @param workbookOutputstream 输出的excel数据流，外部负责打开关闭
	 * @param titles 标题
	 * @param fiels 对象属性 和标题对应顺序
	 * @throws Exception
	 */
	public static void exportObject2Excel(List<? extends Object> list,OutputStream workbookOutputstream,String [] titles,String [] fiels)throws Exception 
	{
		if(titles==null|| titles.length==0 || fiels==null || fiels.length==0|| fiels.length!=titles.length)
			throw new Exception("参数错误，fields和titles必须非空的并大小相同");
		Workbook workbook=new XSSFWorkbook(); 
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "第一页");
		
		CellStyle dateCellStyle=workbook.createCellStyle(); 
		short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
		dateCellStyle.setDataFormat(shortDateFormat);
		
		CellStyle centerStyle=workbook.createCellStyle(); 
		centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		centerStyle.setAlignment(HorizontalAlignment.CENTER);
//		centerStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		for(int i=0;i<titles.length;i++)//列宽
		{
//			sheet.autoSizeColumn(i,true);//无效
			int default_width=sheet.getColumnWidth(i);//default_width=2048
			sheet.setColumnWidth(i, default_width*2);
		}
		Row titleRow = sheet.createRow(0);
		for(int i=0;i<titles.length;i++)//写标题
		{
			Cell cell0 = titleRow.createCell(i);
			cell0.setCellValue(titles[i] );
			cell0.setCellStyle(centerStyle);
		}
		int colNum=1;//跳过标题
		for(Object stu:list)
		{
			Row row = sheet.createRow(colNum++);
			for(int f=0;f<fiels.length;f++)
			{
				Object obj=getObjectField(stu,fiels[f] );//反射
				if(obj instanceof Integer || obj instanceof Long)
				{
					row.createCell(f,CellType.NUMERIC).setCellValue(Long.parseLong(obj.toString()));
				}else if(obj instanceof Float ||obj instanceof Double )
				{
					Cell cell = row.createCell(f,CellType.NUMERIC);
					cell.setCellValue(Double.parseDouble(obj.toString()));
					CellStyle cellStyle = workbook.createCellStyle();
					DataFormat df = workbook.createDataFormat();  
					cellStyle.setDataFormat(df.getFormat("#,#0.00")); //小数点后保留两位 
				
					Font font=workbook.createFont();
					//font.setColor(Font.COLOR_RED );
					font.setColor( (short)0xc );
					font.getBold();
					cellStyle.setFont(font);
					cellStyle.setWrapText(true);//换行
					
					cell.setCellStyle(cellStyle);
				}
				else if(obj instanceof Date)
				{
					Cell cell = row.createCell(f);
					
//					String strDate=new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);//yyyy-MM-dd HH:mm:ss
//					cell.setCellValue(strDate);
					//保存为日期格式 
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(dateCellStyle);
					cell.setCellValue((Date)obj);
				}else if(obj instanceof String)
					row.createCell(f).setCellValue((String)obj);
//				XSSFRichTextString rich=new XSSFRichTextString("中华人民共和国");
//				rich.applyFont(font);
			}
		} 
		workbook.write(workbookOutputstream); 
	}
	/**
	 * excel导入功能 
	 * @param workbookInput excel转入流,外部负责打开和关闭
	 * @param clazz 生成类
	 * @param fiels 生成类的字段顺序
	 * @return 转换成类的集合
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
        for(int i=1;i<total;i++)//跳过标题
        {
        	Row row = sheet.getRow(i);
        	//Object obj=new Student ();//通用化
        	T obj=clazz.getDeclaredConstructor().newInstance();
        	for(int f=0;f<fiels.length;f++)
			{	
        		Cell cell = row.getCell(f);  
        		if(cell!=null)
        		{
        			Object value= readCellValue(cell);
    				setObjectField(obj,fiels[f],value );//反射
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
    			if (HSSFDateUtil.isCellDateFormatted(cell)) {//读日期格式
    				res=cell.getDateCellValue(); 
    				break; 
    	        }  
    			 double dbl=cell.getNumericCellValue();//如excel是int 返回double
    			 int num=(int)dbl;
    			 if(dbl-num>0)
    				 res=dbl;
    			 else
    				 res=num;
//     			NumberFormat format=  NumberFormat.getInstance();
//     			res=format.format(value);//转换为字串
    			break; 
    		case  STRING:
    			res=cell.getStringCellValue();
    			break;
    		case  FORMULA: // 公式 
    			//res=  cell.getCellFormula(); 
    			res=cell.getStringCellValue();
    			break;
			default:
				res=cell.getStringCellValue();
    	}
		return res;
	}
	//读对象的属性值
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
	//设置属性值
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
			&&  value instanceof Double)//长整数在excel中显示为科学计数法变Double
		{
			String longStr=new DecimalFormat("#").format((Double)value);
			value=Long.parseLong(longStr);
		}
		if(field.getType()  == String.class && value!=null && value.getClass()!=  String.class)
			//Java是String但在excel中的字串1右对齐显示为数字
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
	
	
	
	private  static List<Student> queryDBData() //模拟数据库数据
	{
		List<Student> list=new ArrayList<>();
		for(int i=0;i<10;i++)
		{
			Student stu=new Student();
			stu.setBirthday(new Date());
			stu.setId(12345678901234L+i);
			stu.setWeight(20000000+i);
			stu.setName("很长很长的名字"+i);
			list.add(stu);
		}
		return list;
	}
	private  static List<Map<String,Object>> queryMapDBData() //模拟数据库数据
	{
		List<Map<String,Object>> list=new ArrayList<>();
		for(int i=0;i<10;i++)
		{
			Map<String,Object> stu=new HashMap();
			stu.put("birthday",new Date());
			stu.put("id",45678901234L+i);
			stu.put("weight",20000000+i);
			stu.put("name","很长很长的名字"+i);
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
		String [] titles=new String[] {"编号","姓名","体重","出生日期"};
		String [] fields=new String[] {"id","name","weight","birthday"};//顺序
		exportObject2Excel(list,workbookOutput,titles,fields);
		workbookOutput.close();
		System.out.println("导出完成");
		
		InputStream workbookInputStream=new FileInputStream("/tmp/workbook.xlsx"); 
		List<Student> bookList=importExcel2Object(workbookInputStream,Student.class,fields);
		workbookInputStream.close();
		System.out.println("导入完成");
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
