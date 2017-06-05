package jdbc.oracleadvance;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

 
public class TesstBloB
{
	/*
 create table BinaryTable
(
  myblob blob,
  myclob clob,
  myraw raw(512),
  mybfile bfile
 );
	 */
	public static void main(String[] arg)
	{
		javax.sql.rowset.serial.SerialBlob  s;
		java.sql.Blob b;
		
		//testBlob1();//OK
		 testBlob2();//OK
		//testRaw();//OK
		 
		//testBfile();//write fail????
	}
	public static void testBlob1()//OK
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@//127.0.0.1:1521/XE";
			Connection conn=DriverManager.getConnection(url,"hr","hr");
			
			
			PreparedStatement prepare=conn.prepareStatement("insert into BinaryTable(myblob) values(?)");
			
			prepare.setObject(1, new byte[]{1,2,3});//OK
			
			int res=prepare.executeUpdate();
			System.out.println("execute result is :"+res);
			prepare.close();
			conn.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public static void testBlob2()//OK
	{
		try
		{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			String url="jdbc:oracle:thin:@//127.0.0.1:1521/XE";
//			Connection conn=DriverManager.getConnection(url,"hr","hr");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@//192.168.1.18:1521/d0posb";
			Connection conn=DriverManager.getConnection(url,"mpmtdata","mpmtdata1234");
			
			PreparedStatement prepare=conn.prepareStatement("delete from BinaryTable");
			prepare.executeUpdate();
			prepare.close();
			
			prepare=conn.prepareStatement("insert into BinaryTable(myblob) values(empty_blob())");
			int res=prepare.executeUpdate();
			System.out.println("execute result is :"+res);
			prepare.close();
			
			
			prepare=conn.prepareStatement("update BinaryTable  set  myblob=?");
			
			//OK
			InputStream fis = new FileInputStream("C:\\temp\\code.png");//blob类型 PL/SQL Developer 可以查看图片
			prepare.setBinaryStream(1,fis);
			
			//prepare.setBytes(1, "lisi".getBytes());//OK
			
			res=prepare.executeUpdate();
			System.out.println("execute result is :"+res);
			
			prepare.close();
			
			{//读OK
				prepare=conn.prepareStatement("select  myblob from  BinaryTable ");
				prepare.executeQuery();
				ResultSet rs=prepare.getResultSet();
				while(rs.next())
				{
					InputStream input=rs.getBlob(1).getBinaryStream();
					FileOutputStream fileOut=new FileOutputStream("C:/temp/code_copy.png");
					byte[] buffer=new byte[1024];
					int len=0;
					while((len=input.read(buffer))!=-1)
					{
						fileOut.write(buffer,0,len);
					}
				}
			}
		
			conn.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	public static void testRaw()//OK 表中raw 类型列长度要求最大2000,是二进制图片 PLSQL Developer不能查看
	{
		try
		{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			String url="jdbc:oracle:thin:@//127.0.0.1:1521/XE";
//			Connection conn=DriverManager.getConnection(url,"hr","hr");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@//192.168.1.18:1521/d0posb";
			Connection conn=DriverManager.getConnection(url,"mpmtdata","mpmtdata1234");
			
			PreparedStatement prepare=conn.prepareStatement("insert into BinaryTable(myraw) values(?)");
			
			//ByteArrayInputStream  strings=new ByteArrayInputStream("hello".getBytes());
			InputStream fis = new FileInputStream("C:\\temp\\code.png");
			prepare.setBinaryStream(1,fis);
			
			int res=prepare.executeUpdate();
			System.out.println("execute result is :"+res);
			prepare.close();
			conn.close();
			fis.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void testBfile()   //fail????
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@//192.168.1.18:1521/d0posb";
			Connection conn=DriverManager.getConnection(url,"mpmtdata","mpmtdata1234");
			
			{
			
//				PreparedStatement prepare=conn.prepareStatement("insert into BinaryTable(mybfile) values(?)");
//				//prepare.setBytes(1, "lisi".getBytes());//报错
//				//prepare.setBinaryStream(1,new FileInputStream("C:\\temp\\code.png"));//报错
//				int res=prepare.executeUpdate();
//				System.out.println("execute result is :"+res);
//				prepare.close();
			}
			 
			{//未试 ??? 
				PreparedStatement prepare=conn.prepareStatement("select  mybfile from  BinaryTable ");
				ResultSet rs=prepare.getResultSet();
				
				while(rs.next())
				{
					InputStream input=rs.getBinaryStream(1);
					
				}
			}
			conn.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
}
