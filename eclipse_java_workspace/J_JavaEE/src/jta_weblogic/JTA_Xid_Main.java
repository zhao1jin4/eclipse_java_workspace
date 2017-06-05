package jta_weblogic;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import oracle.jdbc.xa.client.OracleXADataSource;

public class JTA_Xid_Main 
{
	//测试OK
	public static XADataSource newOracleXADataSource() throws Exception
	{
		OracleXADataSource oracleXADS=new OracleXADataSource();//oracle.jdbc.xa.client.
		oracleXADS.setURL("jdbc:oracle:thin:@127.0.0.1:1521/XE");
		oracleXADS.setUser("hr");
		oracleXADS.setPassword("hr");
		oracleXADS.setPortNumber(1521);
		oracleXADS.setDatabaseName("XE");
		oracleXADS.setServerName("127.0.0.1");
		return oracleXADS;
	}
	
	//是用“两步提交协议”来提交一个事务分支：
	 public void  test()throws Exception
	{
		XADataSource xaDS;
		XAConnection xaCon;
		XAResource xaRes;
		Xid xid;
		Connection con;
		Statement stmt;
		int ret;
		xaDS = newOracleXADataSource();
		xaCon = xaDS.getXAConnection();
		xaRes = xaCon.getXAResource();
		con = xaCon.getConnection();
		stmt = con.createStatement();
		xid = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});//实现Xid接口的类, 类用来标识事务
		try 
		{
			xaRes.start(xid, XAResource.TMNOFLAGS);
			stmt.executeUpdate("insert into  h2_score(id,score) values(1,70)");
			xaRes.end(xid, XAResource.TMSUCCESS);
			ret = xaRes.prepare(xid);
			if (ret == XAResource.XA_OK) 
				xaRes.commit(xid, false);
		}
		catch (XAException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			stmt.close();
			con.close();
			xaCon.close();
		}
	}

	class MyXid implements Xid
	{
		protected int formatId;
		protected byte gtrid[];
		protected byte bqual[];
		public MyXid()
		{
		}
		public MyXid(int formatId, byte gtrid[], byte bqual[])
		{
			this.formatId = formatId;
			this.gtrid = gtrid;
			this.bqual = bqual;
		}
		public int getFormatId()
		{
			return formatId;
		}
		
		public byte[] getBranchQualifier()
		{
			return bqual;
		}
		public byte[] getGlobalTransactionId()
		{
			return gtrid;
		}
	}
	public static void main(String[] args) throws Exception 
	{
		new JTA_Xid_Main().test();
	}
}
