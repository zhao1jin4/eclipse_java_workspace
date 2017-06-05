package jdbc.oracleadvance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TimeOut {
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
		
		
//		PreparedStatement prepare= conn.prepareStatement(" select * from employees for update");
//		prepare.setQueryTimeout(10); //对锁,有用的
//		prepare.executeQuery();
//		prepare.close();
//		
		
		PreparedStatement prepare2 = conn.prepareStatement(" update employees set salary=salary+1 where employee_id=100");
		prepare2.setQueryTimeout(10); //这个好像没用的??
		prepare2.execute();
		prepare2.close();
		
		conn.close();
		
	}
	
	
}
