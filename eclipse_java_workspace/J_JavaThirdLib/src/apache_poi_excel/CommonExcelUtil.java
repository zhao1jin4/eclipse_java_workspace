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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
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
	 * @param workbookOutputstream 输出的excel数据流，外部负责打开关闭(为web ServletOutputStream)
	 * @param titles 标题
	 * @param fields 对象属性 和标题对应顺序
	 * @throws Exception
	 */
	public static void exportObject2Excel(List<? extends Object> list,OutputStream workbookOutputstream,String [] titles,String [] fields)throws Exception 
	{
		if(titles==null|| titles.length==0 || fields==null || fields.length==0|| fields.length!=titles.length)
			throw new Exception("参数错误，fields和titles必须非空的并大小相同");
		Workbook workbook=new XSSFWorkbook(); 
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "第一页"); 
		
		//基本样式
		CellStyle titleStyle=workbook.createCellStyle(); 
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN); 

		Font font=workbook.createFont();
		//font.setColor(Font.COLOR_RED );
		font.setColor( IndexedColors.BLACK.getIndex());//文字颜色 (short)0xc 
		//font.getBold();
		titleStyle.setFont(font);
		titleStyle.setWrapText(true);//换行 ,如数据要换行写入\n即可
		//数据样式
		CellStyle dataCellStyle=workbook.createCellStyle(); 
		dataCellStyle.cloneStyleFrom(titleStyle);
		dataCellStyle.setWrapText(false);

		//日期样式
		CellStyle dateCellStyle=workbook.createCellStyle(); 
		dateCellStyle.cloneStyleFrom(dataCellStyle);
		short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
		dateCellStyle.setDataFormat(shortDateFormat);
		//颜色样式
		CellStyle yellowStyle=workbook.createCellStyle(); 
		yellowStyle.cloneStyleFrom(dataCellStyle);
		yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		//小数样式
		CellStyle numCellStyle = workbook.createCellStyle();
		numCellStyle.cloneStyleFrom(dataCellStyle);
		DataFormat df = workbook.createDataFormat();  
		numCellStyle.setDataFormat(df.getFormat("#,#0.00")); //小数点后保留两位 
		
		Row titleRow = sheet.createRow(0);
		sheet.createFreezePane(0, 1);//标题行冻结
		
		for(int i=0;i<titles.length;i++) 
		{
			//写标题
			Cell cell0 = titleRow.createCell(i);
			cell0.setCellValue(titles[i] );
			cell0.setCellStyle(titleStyle);
			
			//列宽
//			sheet.autoSizeColumn(i,true);//无效
			int default_width=sheet.getColumnWidth(i);//default_width=2048
			sheet.setColumnWidth(i, default_width*2);
			//sheet.setColumnWidth(i, 0);//隐藏列
		}
		int colNum=1;//跳过标题
		for(Object stu:list)
		{
			Row row = sheet.createRow(colNum++);
			for(int f=0;f<fields.length;f++)
			{
				Cell cell=row.createCell(f);
				cell.setCellStyle(dataCellStyle);
				
				Object obj=getObjectField(stu,fields[f] );//反射
				if(obj==null)
					continue;
				if(obj instanceof Integer || obj instanceof Long)
				{
					cell.setCellType(CellType.NUMERIC);
					cell.setCellValue(Long.parseLong(obj.toString()));
					cell.setCellStyle(yellowStyle);
				}else if(obj instanceof Float ||obj instanceof Double )
				{
					cell.setCellType(CellType.NUMERIC);
					cell.setCellValue(Double.parseDouble(obj.toString())); 
					cell.setCellStyle(numCellStyle);
				}
				else if(obj instanceof Date)
				{  
//					String strDate=new SimpleDateFormat("yyyy-MM-dd").format((Date)obj);//yyyy-MM-dd HH:mm:ss
//					cell.setCellValue(strDate);
					//保存为日期格式 
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(dateCellStyle);
					cell.setCellValue((Date)obj);
				}else if(obj instanceof String)
				{
					//cell.setCellType(CellType.STRING);
					cell.setCellValue((String)obj);
				}
				else //char  
					cell.setCellValue(obj.toString());
				 
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
  OUT:  for(int i=1;i<=total;i++)//跳过标题
        {
        	Row row = sheet.getRow(i);
        	//Object obj=new Student ();//通用化
        	T obj=clazz.getDeclaredConstructor().newInstance();
        	for(int f=0;f<fiels.length;f++)
			{	
        		Cell cell = row.getCell(f);  
        		if(cell!=null)
        		{
        			if(cell.getCellStyle().getBorderBottom()==BorderStyle.NONE)//防止用户多输入了一行空白
        				break OUT;
        			Object value= readCellValue(cell);
    				setObjectField(obj,fiels[f],value );//反射
        		}
			}
        	res.add(obj); 
        }
		return res;
	}
	public  static Object readCellValue(Cell cell)//private
	{
		CellType type=cell.getCellType(); 
		Object res=null;
		 
		switch (type)
    	{
    		case NUMERIC : 
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
		
		if(value instanceof String  )
		{
			if(field.getType() == Timestamp.class || field.getType() ==Date.class) 
				value=null;
			else if(field.getType() == Boolean.class || field.getType() ==boolean.class) 
				value=false;
			else if(field.getType() == long.class || field.getType() == Long.class)
			{
				if("".equals(value))//什么都没写
					value=0L;
				else
				{
					try{
						value=Long.parseLong(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					}
				}
			}
			else if(field.getType() == Integer.class || field.getType() ==int.class )
			{
				if("".equals(value))//什么都没写
					value=0;
				else
				{
					try{
						value=Integer.parseInt(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					}
					
				}
			}
			else if(field.getType() == double.class || field.getType() ==Double.class)
			{
				if("".equals(value))//什么都没写
					value=0d;
				else
				{
					try{
						value=Double.parseDouble(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					}
				}
				
			}
			else if(field.getType() == float.class || field.getType() ==Float.class)
			{
				if("".equals(value))//什么都没写
					value=0f;
				else
				{
					try{
						value=Float.parseFloat(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					} 
				}
			}
			else //if( value.getClass()!=  String.class)
				//Java是String但在excel中的字串1右对齐显示为数字	
			{
				value=value.toString();
			} 
			
		}else
		{//value非String
			if(field.getType() == Timestamp.class &&   value instanceof Date)
			{
				value=new Timestamp(((Date)value).getTime());
			} 
			else if  (field.getType()  == Long.class || field.getType()  == long.class )
			{
				if( value instanceof Integer)
				{
					try{
						value=Long.parseLong(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					}
				}else  if( value instanceof Double)//长整数在excel中显示为科学计数法变Double
				{
					String longStr=new DecimalFormat("#").format((Double)value); 
					try{
						value=Long.parseLong(longStr);
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
					}
				} 
			}else if  //java 是Double 但excel没写小数点是整数
			(
				(field.getType()  == Double.class 
				 || field.getType()  == double.class
				)	
				&&  value.getClass()!=  Double.class 
				&& value.getClass()!=  double.class
			)	 
			{
				try{
					value=Double.parseDouble(value.toString());
				}catch(Exception e)
				{
					e.printStackTrace();
					throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
				}
			} 
		}//value非string
		
		//为数值类型超长报错,类型不匹配错
		if(field.getType() == Long.class || field.getType() == long.class 
			|| field.getType() == Integer.class || field.getType() == int.class 
			|| field.getType() == Double.class || field.getType() == double.class
			|| field.getType() == Float.class || field.getType() == float.class 
		)
		{
			try{
				field.set(obj,value); 
			}catch(Exception e)
			{
				e.printStackTrace();
				throw new NumberFormatException(value+"值不正确，请输入正确的数值，不要超长"); //最好自定义异常
			}
		} 
//		else if(field.getType() == Date.class)
//		{
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//"yyyy-MM-dd HH:mm:ss"
//			Date date=format.parse(value.toString());
//			field.set(obj,date); 
//		}
		else
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
		String [] titles=new String[] {"编号","姓名","体重","出生日期"};
		String [] fields=new String[] {"id","name","weight","birthday"};//顺序
		
		/*
		OutputStream workbookOutput=new FileOutputStream("/tmp/workbook.xlsx");//ServletOutputStream
		exportObject2Excel(list,workbookOutput,titles,fields);
		workbookOutput.close();
		System.out.println("导出完成");
		*/
		InputStream workbookInputStream=new FileInputStream("/tmp/workbook.xlsx"); 
		List<Student> bookList=importExcel2Object(workbookInputStream,Student.class,fields);
		workbookInputStream.close();
		System.out.println("导入完成");
		System.out.println(bookList);
		
//		long x=91111111111111112222L;  
//		Long n=Long.parseLong("91111111111111112222");//Spring MVC ，JSON接入Long类型，按int操作的，要研究下
		
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
