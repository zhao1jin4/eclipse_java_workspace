package jdbc.oracleadvance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TestCursorMainApp {

	public static void main(String[] args) throws Exception 
	{
	
		CallableStatement cstmt;
		ResultSet cursor;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
		
		
		
		// Use a PL/SQL block to open the cursor
		cstmt = conn.prepareCall
		         ("begin open ? for select first_name from employees; end;");

		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		cstmt.execute();
		cursor = ((OracleCallableStatement)cstmt).getCursor(1);

		// Use the cursor like a normal ResultSet
		while (cursor.next ())
		    {System.out.println (cursor.getString(1));}
		
		
		conn.close();
	}

}
