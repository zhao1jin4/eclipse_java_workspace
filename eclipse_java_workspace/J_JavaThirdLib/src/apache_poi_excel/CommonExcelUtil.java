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
	 * ����excel
	 * @param list Ҫʹ�õ�����
	 * @param workbookOutputstream �����excel���������ⲿ����򿪹ر�(Ϊweb ServletOutputStream)
	 * @param titles ����
	 * @param fields �������� �ͱ����Ӧ˳��
	 * @throws Exception
	 */
	public static void exportObject2Excel(List<? extends Object> list,OutputStream workbookOutputstream,String [] titles,String [] fields)throws Exception 
	{
		if(titles==null|| titles.length==0 || fields==null || fields.length==0|| fields.length!=titles.length)
			throw new Exception("��������fields��titles����ǿյĲ���С��ͬ");
		Workbook workbook=new XSSFWorkbook(); 
		Sheet sheet = workbook.createSheet();
		workbook.setSheetName(0, "��һҳ"); 
		
		//������ʽ
		CellStyle titleStyle=workbook.createCellStyle(); 
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN); 

		Font font=workbook.createFont();
		//font.setColor(Font.COLOR_RED );
		font.setColor( IndexedColors.BLACK.getIndex());//������ɫ (short)0xc 
		//font.getBold();
		titleStyle.setFont(font);
		titleStyle.setWrapText(true);//���� ,������Ҫ����д��\n����
		//������ʽ
		CellStyle dataCellStyle=workbook.createCellStyle(); 
		dataCellStyle.cloneStyleFrom(titleStyle);
		dataCellStyle.setWrapText(false);

		//������ʽ
		CellStyle dateCellStyle=workbook.createCellStyle(); 
		dateCellStyle.cloneStyleFrom(dataCellStyle);
		short shortDateFormat=workbook.createDataFormat().getFormat("yyyy-mm-dd"); 
		dateCellStyle.setDataFormat(shortDateFormat);
		//��ɫ��ʽ
		CellStyle yellowStyle=workbook.createCellStyle(); 
		yellowStyle.cloneStyleFrom(dataCellStyle);
		yellowStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
		//С����ʽ
		CellStyle numCellStyle = workbook.createCellStyle();
		numCellStyle.cloneStyleFrom(dataCellStyle);
		DataFormat df = workbook.createDataFormat();  
		numCellStyle.setDataFormat(df.getFormat("#,#0.00")); //С���������λ 
		
		Row titleRow = sheet.createRow(0);
		sheet.createFreezePane(0, 1);//�����ж���
		
		for(int i=0;i<titles.length;i++) 
		{
			//д����
			Cell cell0 = titleRow.createCell(i);
			cell0.setCellValue(titles[i] );
			cell0.setCellStyle(titleStyle);
			
			//�п�
//			sheet.autoSizeColumn(i,true);//��Ч
			int default_width=sheet.getColumnWidth(i);//default_width=2048
			sheet.setColumnWidth(i, default_width*2);
			//sheet.setColumnWidth(i, 0);//������
		}
		int colNum=1;//��������
		for(Object stu:list)
		{
			Row row = sheet.createRow(colNum++);
			for(int f=0;f<fields.length;f++)
			{
				Cell cell=row.createCell(f);
				cell.setCellStyle(dataCellStyle);
				
				Object obj=getObjectField(stu,fields[f] );//����
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
					//����Ϊ���ڸ�ʽ 
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
  OUT:  for(int i=1;i<=total;i++)//��������
        {
        	Row row = sheet.getRow(i);
        	//Object obj=new Student ();//ͨ�û�
        	T obj=clazz.getDeclaredConstructor().newInstance();
        	for(int f=0;f<fiels.length;f++)
			{	
        		Cell cell = row.getCell(f);  
        		if(cell!=null)
        		{
        			if(cell.getCellStyle().getBorderBottom()==BorderStyle.NONE)//��ֹ�û���������һ�пհ�
        				break OUT;
        			Object value= readCellValue(cell);
    				setObjectField(obj,fiels[f],value );//����
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
		
		if(value instanceof String  )
		{
			if(field.getType() == Timestamp.class || field.getType() ==Date.class) 
				value=null;
			else if(field.getType() == Boolean.class || field.getType() ==boolean.class) 
				value=false;
			else if(field.getType() == long.class || field.getType() == Long.class)
			{
				if("".equals(value))//ʲô��ûд
					value=0L;
				else
				{
					try{
						value=Long.parseLong(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					}
				}
			}
			else if(field.getType() == Integer.class || field.getType() ==int.class )
			{
				if("".equals(value))//ʲô��ûд
					value=0;
				else
				{
					try{
						value=Integer.parseInt(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					}
					
				}
			}
			else if(field.getType() == double.class || field.getType() ==Double.class)
			{
				if("".equals(value))//ʲô��ûд
					value=0d;
				else
				{
					try{
						value=Double.parseDouble(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					}
				}
				
			}
			else if(field.getType() == float.class || field.getType() ==Float.class)
			{
				if("".equals(value))//ʲô��ûд
					value=0f;
				else
				{
					try{
						value=Float.parseFloat(value.toString());
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					} 
				}
			}
			else //if( value.getClass()!=  String.class)
				//Java��String����excel�е��ִ�1�Ҷ�����ʾΪ����	
			{
				value=value.toString();
			} 
			
		}else
		{//value��String
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
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					}
				}else  if( value instanceof Double)//��������excel����ʾΪ��ѧ��������Double
				{
					String longStr=new DecimalFormat("#").format((Double)value); 
					try{
						value=Long.parseLong(longStr);
					}catch(Exception e)
					{
						e.printStackTrace();
						throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
					}
				} 
			}else if  //java ��Double ��excelûдС����������
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
					throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
				}
			} 
		}//value��string
		
		//Ϊ��ֵ���ͳ�������,���Ͳ�ƥ���
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
				throw new NumberFormatException(value+"ֵ����ȷ����������ȷ����ֵ����Ҫ����"); //����Զ����쳣
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
		String [] titles=new String[] {"���","����","����","��������"};
		String [] fields=new String[] {"id","name","weight","birthday"};//˳��
		
		/*
		OutputStream workbookOutput=new FileOutputStream("/tmp/workbook.xlsx");//ServletOutputStream
		exportObject2Excel(list,workbookOutput,titles,fields);
		workbookOutput.close();
		System.out.println("�������");
		*/
		InputStream workbookInputStream=new FileInputStream("/tmp/workbook.xlsx"); 
		List<Student> bookList=importExcel2Object(workbookInputStream,Student.class,fields);
		workbookInputStream.close();
		System.out.println("�������");
		System.out.println(bookList);
		
//		long x=91111111111111112222L;  
//		Long n=Long.parseLong("91111111111111112222");//Spring MVC ��JSON����Long���ͣ���int�����ģ�Ҫ�о���
		
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
