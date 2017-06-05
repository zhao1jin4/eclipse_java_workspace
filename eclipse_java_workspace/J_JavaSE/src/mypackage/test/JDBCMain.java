package mypackage.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;

public class JDBCMain {

//	public static String dirver="org.h2.Driver";
//	public static String url="jdbc:h2:tcp://localhost/~/test";//test�����ݿ���
//	public static String username="sa";
//	public static String password="";
	
//	public static String dirver="oracle.jdbc.driver.OracleDriver";
//	public static String url="jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	public static String username="hr";
//	public static String password="hr";
	
	public static String dirver="oracle.jdbc.driver.OracleDriver";
	public static String url="jdbc:oracle:thin:@192.168.1.18:1521/d0posb";
	public static String username="mpmtdata";
	public static String password="mpmtdata1234";
	
	public static void mateData() {
		try {
			Class.forName(dirver);
			Connection conn=DriverManager.getConnection(url,username,password);
			DatabaseMetaData dbMetaData= conn.getMetaData();
			ResultSet tablesRS=dbMetaData.getTables(null, null, null, new String[]{"TABLE"});
			System.out.println("=======���еı�:");
			while(tablesRS.next())
			{
				System.out.println(tablesRS.getString("TABLE_NAME"));
			}
			
			long maxRow=dbMetaData.getMaxRowSize();//0
			String dbName=dbMetaData.getDatabaseProductName();//Oracle
			int dbMajor=dbMetaData.getDatabaseMajorVersion();//11
			
			PreparedStatement prepare=conn.prepareStatement("select * from student");
			ResultSetMetaData tableMetaData=prepare.getMetaData();
			System.out.println("=======student�����:");
			int count=tableMetaData.getColumnCount();
			for(int i=1;i<=count;i++) 
			{
				System.out.println(tableMetaData.getColumnName(i)+":����"+tableMetaData.getColumnTypeName(i));//��1��ʼ
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	  create or replace procedure myproc (id IN integer,name out varchar2) is  --as 
	   l_tmp integer:=1;
	   begin
	   	name:= (id + l_tmp) ||'lisi';
	   end;
	   /
	 */
	public static void procedure() {
		try {
			Class.forName(dirver);
			Connection conn=DriverManager.getConnection(url,username,password);
			CallableStatement call=conn.prepareCall("call myproc(?,?)");
			call.registerOutParameter(2, Types.VARCHAR);
			call.setInt(1, 123);
			call.execute();
			String result=call.getString(2);
			System.out.println(result);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void batch() 
	{
		try {
			Class.forName(dirver);
			Connection conn=DriverManager.getConnection(url,username,password);
			PreparedStatement prepare=conn.prepareStatement("insert into student(name) values(?)");
			
			for(int i=0;i<100;i++)
			{
				prepare.setString(1,"lisi_"+i);
				prepare.addBatch();
				if(i%10==0)
					prepare.executeBatch();
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void reverseResultSet() {
		try {
			Class.forName(dirver);
			Connection conn=DriverManager.getConnection(url,username,password);
			PreparedStatement prepare=conn.prepareStatement("select * from student",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=prepare.executeQuery();
			int i=0;
			
			while(rs.next())
			{
//				rs.getRow();
//				rs.absolute(rowNum);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void oracleChar() throws Exception
	{
		String str="j_varchar����4567890";//һ�������ַ���Java��ռ�����ֽ� ,��Oracle��AL32UTF8,varchar2,nvarchar2
		System.out.println(str.getBytes().length);
		
		Class.forName(dirver);
		Connection conn=DriverManager.getConnection(url,username,password);
		PreparedStatement prepare=conn.prepareStatement("select * from tmp");
		ResultSet rs=prepare.executeQuery();
		String dbStr;
		while(rs.next())  //oracle -> java һ������ռ�����ֽ�
		{
//			dbStr=rs.getString(1);//��1��ʼ
//			dbStr=rs.getString(2); 
			
			dbStr=rs.getString("common_name");
			System.out.println(dbStr.getBytes().length);
			
			dbStr=rs.getString("n_name");
			System.out.println(dbStr.getBytes().length);
		}
		
//		 prepare=conn.prepareStatement("insert into tmp (common_name,n_name)values(?,?)");
//		 prepare.setString(1, "j_varchar����4567890");//��1��ʼ  , varchar2���ʱ��ͳ��˷�Χ������DB�ͳ���22������(java->oracle varchar2 ʱһ������ռ3���ֽ�)
//		 prepare.setString(2, "j_nvarchar����567890");// ( java->oracle nvarchar2  ʱһ������ռ2���ֽ� )
//		 prepare.execute();
		 
		conn.close();
		/**
		 * create table tmp (
		 * 	common_name varchar2(20),
		 *  n_name nvarchar2(20)
		 * );
		 * insert into tmp (common_name,n_name)values('varchar����','nvarchar����');
		 */
	}
	public static void main(String[] args) throws Exception {
		//mateData();
		procedure() ;
		//oracleChar() ;
	}

}
