package jdknew;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class JDKNewFeature
{
	//jdk6����
	public void printf(String format, Object ...args) //args�ڷ���������һ��Object������
	{
		Object[] o=args;
	}
    
	public static void JDK7New() throws Exception 
	{
		try{
			String a=null;
			a.charAt(0);
			InputStream  in=new FileInputStream("c:/temp/aa.txt"); //ʵ��AutoCloseable�ӿڵ��Զ��ر�
		}catch( NullPointerException | FileNotFoundException e  ) // ����쳣�� | 
		{
			e.printStackTrace();
		}
		
		int billion=1_000_000_000;//��������ʹ���»���
		int binary=0b1001_1001;  //0b�Ƕ�����
		
		 switch("one")  //switch�����ִ�
		 {  
	         case "one":  
	             System.err.println("1");  
	             break;  
	         case "two":  
	             System.out.println("2");  
	             break;  
	         default :  
	             System.out.println("err");  
		 }  
		 Map<String, List<String>> myMap = new HashMap<>(); //���Լ�д
		
		BufferedInputStream bufferInput=new BufferedInputStream(new FileInputStream(""));
		new ZipInputStream(bufferInput,Charset.defaultCharset());//�ɼӱ���
		
		 try (BufferedReader br = new BufferedReader(new FileReader("")))  //try ()�п��ԼӴ���,���û��catch��finally
        {  
          String a= br.readLine();  
        }  
		 
	}
	public static void JDK7RowSet( ) 
	{
		
		try
		{ 
			//Class.forName("org.h2.Driver");
			
			RowSetFactory  rowSetFactory = RowSetProvider.newFactory();//��ȱʡ��RowSetFactory ʵ�� 
			JdbcRowSet rowSet = rowSetFactory.createJdbcRowSet(); 
			String url="jdbc:h2:tcp://localhost:9092/test";
			rowSet.setUrl(url); 
			rowSet.setUsername("sa"); 
			rowSet.setPassword(""); 
			rowSet.setCommand("SELECT * FROM EMPLOYEE where DEPARTMENT_ID  =? ");
			rowSet.setInt(1, 10);
			rowSet.execute(); 
			while(rowSet.next())
			{
			  System.out.println(rowSet.getString("USERNAME"));
			}
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	 
	public static void main(String... args)throws Exception //main����д��
	{
		//JDK7New();
		//JDK7RowSet();
	 
	}	

} 