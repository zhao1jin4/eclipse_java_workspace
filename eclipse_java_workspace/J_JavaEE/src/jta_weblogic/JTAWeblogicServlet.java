package jta_weblogic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/*
XA连接是一个JTA事务中的参与者。这就意味着XA连接不支持JDBC的自动提交特性。
也就是说应用程序不必在XA连接上调用java.sql.Connection.commit()或java.sql.Connection.rollback()。
相反，应用程序应该使用UserTransaction.begin()、UserTransaction.commit()和UserTransaction.rollback().


weblogic中使用JTA,要在数据库服务器上启用 XA 
：@xaview.sql   (以sys用户执行)

xaview.sql 脚本驻留在 $ORACLE_HOME/rdbms/admin 目录中

授予下列许可权限：
   grant select on v$xatrans$ to public (or <user>);
   grant select on pending_trans$ to public;
   grant select on dba_2pc_pending to public;
   grant select on dba_pending_transactions to public;
   （当使用 Oracle Thin 驱动程序 10.1.0.3 或更高版本时）
   grant execute on dbms_system to <user>;
*/

/*
create table oracle_score(id int primary key ,score  int);
insert into  oracle_score(id,score) values(1,80);
commit;

create table mysql_score(id int primary key ,score  int);
insert into  mysql_score(id,score) values(1,60);
commit;
*/

//测试OK
@WebServlet(urlPatterns = "/weblogicJTA")
public class JTAWeblogicServlet extends HttpServlet 
{
	public XADataSource newMySQLXADataSource()
	{
		MysqlXADataSource mysqlXADS=new MysqlXADataSource(); //com.mysql.jdbc.jdbc2.optional.
		mysqlXADS.setUser("root");
		mysqlXADS.setPassword("root");
		String url="jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
		mysqlXADS.setUrl(url);
		mysqlXADS.setURL(url);
		int port=3306;
		mysqlXADS.setPort(port);
		mysqlXADS.setPortNumber(port);
		mysqlXADS.setServerName("127.0.0.1");
		mysqlXADS.setDatabaseName("test");
		return mysqlXADS;
	}
	public void executeInJndiConnection(Connection conn) throws SQLException
    {
       PreparedStatement pstmt = conn.prepareStatement("update oracle_score set score=score-1 where id=1"); 
       //PreparedStatement pstmt = conn.prepareStatement("insert into oracle_score(id,score)values(1,88)");//使用主键重复的错误测试
        pstmt.executeUpdate();//如有错误,这里就抛出,无论是否设置setAutoCommit(false)
        pstmt.close();
    }
    public void executeInNewConnection(Connection conn ) throws SQLException
    {
        PreparedStatement pstmt = conn.prepareStatement("update mysql_score set score=score+1 where id=1"); 
        pstmt.executeUpdate();
        pstmt.close();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		TransactionManager trans = null;
        Connection jndiConn = null;
        Connection newConn = null;
        try {
        	Properties properties = new Properties();
    		properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
    		properties.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
            Context context = new InitialContext(properties);
            
            Object jndiObj= context.lookup("jdbc/oracleXA"); 
            //weblogic配置时一定要选择XA JDBC driver,driver自动配置为oracle.jdbc.xa.client.OracleXADataSource
            DataSource jndiDataSource = (DataSource)jndiObj;//不可强转到XADataSource
            jndiConn = jndiDataSource.getConnection();
            //jndiConn.setAutoCommit(false); //可以不加的
            trans = (TransactionManager)context.lookup("javax.transaction.UserTransaction");//返回的也是UserTransaction,
            trans.begin();  
            
            XADataSource xaDataSource = newMySQLXADataSource();//这个数据源是通过JDBC配置的
            XAConnection xaConn = xaDataSource.getXAConnection();
            XAResource xaRes = xaConn.getXAResource();   
            trans.getTransaction().enlistResource(xaRes);//需要主动的加入到当前的事务中      
            //如.class不在weblogic中则报错You may enlist a resource only on a server.
            //就是说weblogic的JTA TransactionManager只能在代码(war或者ear)发布到weblogic的server上才能使用.
            newConn = xaConn.getConnection();
            //newConn.setAutoCommit(false);//可以不加的
            executeInNewConnection(newConn); 
            executeInJndiConnection(jndiConn);//通过在weblogic上配置的DataSource,会自动加入到当前的事务中. 
            trans.commit();
            System.out.println("OK!transaction manager commited!");
        } catch (Exception e)
        {    
        	 e.printStackTrace();
            try {
                trans.rollback();
                System.out.println("Exception!rollback transactions managed by traMgr.");
            } catch (Exception e1) {
                e1.printStackTrace();
            } 
        }finally{
            try {
                jndiConn.close();
                newConn.close();
            } catch (SQLException e) {e.printStackTrace();}     
        }
	}
}
