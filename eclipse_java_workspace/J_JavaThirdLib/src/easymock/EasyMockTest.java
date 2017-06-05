package easymock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;

public class EasyMockTest
{
	@Test
	public  void testMockObject() throws Exception 
	{
		IMocksControl control = EasyMock.createControl();//使用多个 Mock 对象
		ResultSet mockResultSet = control.createMock(ResultSet.class);//调用顺序是不进行检查
		//----
		mockResultSet.next();
		EasyMock.expectLastCall().andReturn(true).times(3);//对next方法调3次返回true
		EasyMock.expectLastCall().andReturn(false).times(1);//再调一次返回false

		mockResultSet.getString(1);//对getString(1)方法调用的返回
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_001").times(1);
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_002").times(1);
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_003").times(1);

		mockResultSet.getDouble(2);
		EasyMock.expectLastCall().andReturn(350.0).times(1);
		EasyMock.expectLastCall().andReturn(1350.0).times(1);
		EasyMock.expectLastCall().andReturn(15350.0).times(1);
		
		control.replay();//调用后mockResultSet就有值可用了
		
		  int i = 0;
	      String[] priceLevels = { "Level_A", "Level_B", "Level_C" };
	      while (mockResultSet.next()) 
	      {
	        SalesOrder order = new SalesOrderImpl();
	        order.loadDataFromDB(mockResultSet);
	        Assert.assertEquals(order.getPriceLevel(), priceLevels[i]);
	        i++;
	      }
	  control.verify();//验证方法调用真的完成了,如调用次数
	}
	public  void testMockObjectInvokeOrder() throws Exception 
	{
		//----
		IMocksControl control = EasyMock.createStrictControl();
		ResultSet mockResultSet = control.createMock(ResultSet.class);//会检查调用方法的顺序
		//----
		for(int i=0;i<3;i++)
		{
			mockResultSet.next();
			EasyMock.expectLastCall().andReturn(true).times(1);//对next方法调1次返回true
			
			mockResultSet.getString(1);//对getString(1)方法调用的返回
			EasyMock.expectLastCall().andReturn("DEMO_ORDER_00"+i).times(1);

			mockResultSet.getDouble(2);
			EasyMock.expectLastCall().andReturn(350.0 *(Math.pow(10,i)) ).times(1);
		}
		mockResultSet.next();
		EasyMock.expectLastCall().andReturn(false).times(1);//再调一次返回false
		
		
		control.replay();//调用后mockResultSet就有值可用了
		
		  int i = 0;
	      String[] priceLevels = { "Level_A", "Level_B", "Level_C" };
	      while (mockResultSet.next()) 
	      {
	        SalesOrder order = new SalesOrderImpl();
	        order.loadDataFromDB(mockResultSet);
	        Assert.assertEquals(order.getPriceLevel(), priceLevels[i]);
	        i++;
	      }
	  control.verify();//验证方法调用真的完成了,如调用次数
	}
	
	
	
	private String sqlEquals(String in)
	{
		EasyMock.reportMatcher(new SQLEquals(in));//注册自定义,implements IArgumentMatcher ,实现matches方法
		return in;
	}
	public  void  MockObjectMain() throws Exception 
	{
		ResultSet mockResultSet = EasyMock.createMock(ResultSet.class);//只需要一个 Mock 对象,调用顺序是不进行检查
		
		IMocksControl control = EasyMock.createControl();//使用多个 Mock 对象
		java.sql.Connection mockConnection = control.createMock(Connection.class);//调用顺序是不进行检查
		java.sql.Statement mockStatement = control.createMock(Statement.class);
		// Mock 对象将会经历两个状态：Record 状态和 Replay 状态
		//---Mock 对象一经创建，它的状态就被置为 Record
		
		mockResultSet.getString(1);
		EasyMock.expectLastCall().andReturn("My return value");//预期返回值
		
		mockStatement.executeQuery("SELECT * FROM sales_order_table");//有大小写的问题
		mockStatement.executeQuery( EasyMock.anyObject().toString() );
		mockStatement.executeQuery( sqlEquals("SELECT * FROM sales_order_table"));
		
		EasyMock.expectLastCall().andStubReturn(mockResultSet);//方法的调用总是返回一个相同的值
		SQLException exception=new SQLException();
		EasyMock.expectLastCall().andThrow(exception);//预期异常抛出
		EasyMock.expectLastCall().andStubThrow(exception);//设定抛出默认异常
		
		mockResultSet.getString(1);
		EasyMock.expectLastCall().andReturn("My return value").times(3);//预期方法调用次数
		
		mockResultSet.close();
		EasyMock.expectLastCall().times(3, 5);//3-5次 atLeastOnce(),anyTimes()
		//---Replay 状态
		EasyMock.replay(mockResultSet);
		control.replay();
		//...
		
		//为了避免生成过多的 Mock 对象，EasyMock 允许对原有 Mock 对象进行重用,如要重新初始化，我们可以采用 reset 方法
		EasyMock.reset(mockResultSet);
		control.reset();
		
		EasyMock.verify(mockResultSet);
		control.verify();
		
		{
			ResultSet strickMockResultSet = EasyMock.createStrictMock(ResultSet.class);//会检查调用方法的顺序
			
			IMocksControl strictControl = EasyMock.createStrictControl();
			ResultSet strickMockResultSet1 = strictControl.createMock(ResultSet.class);//会检查调用方法的顺序
		}
	}
	@Test
	public  void  testCommonReturn() throws Exception 
	{
		IMocksControl mocksControl = EasyMock.createControl();
		SalesOrderImpl mockObject=mocksControl.createMock(SalesOrderImpl.class);
		
		
		
		//匹配指定参数
		mockObject.getProductPrice("tomota");
		EasyMock.expectLastCall().andStubReturn(45.0f);
	
		//匹配所有参数 放在最后  OK
		mockObject.getProductPrice(EasyMock.isA(String.class));
		EasyMock.expectLastCall().andStubReturn(55.0f);
		
		mocksControl.replay();
		
		
		//---special
		float price=mockObject.getProductPrice("tomota");
		System.out.println("tomota"+price);
		Assert.assertEquals(45.0f, price, 0.0f);
		//---common
		price=mockObject.getProductPrice("common");
		System.out.println("common"+price);
		Assert.assertEquals(55.0f, price, 0.0f);
		
	}
}
