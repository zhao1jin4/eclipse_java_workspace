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
		IMocksControl control = EasyMock.createControl();//ʹ�ö�� Mock ����
		ResultSet mockResultSet = control.createMock(ResultSet.class);//����˳���ǲ����м��
		//----
		mockResultSet.next();
		EasyMock.expectLastCall().andReturn(true).times(3);//��next������3�η���true
		EasyMock.expectLastCall().andReturn(false).times(1);//�ٵ�һ�η���false

		mockResultSet.getString(1);//��getString(1)�������õķ���
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_001").times(1);
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_002").times(1);
		EasyMock.expectLastCall().andReturn("DEMO_ORDER_003").times(1);

		mockResultSet.getDouble(2);
		EasyMock.expectLastCall().andReturn(350.0).times(1);
		EasyMock.expectLastCall().andReturn(1350.0).times(1);
		EasyMock.expectLastCall().andReturn(15350.0).times(1);
		
		control.replay();//���ú�mockResultSet����ֵ������
		
		  int i = 0;
	      String[] priceLevels = { "Level_A", "Level_B", "Level_C" };
	      while (mockResultSet.next()) 
	      {
	        SalesOrder order = new SalesOrderImpl();
	        order.loadDataFromDB(mockResultSet);
	        Assert.assertEquals(order.getPriceLevel(), priceLevels[i]);
	        i++;
	      }
	  control.verify();//��֤����������������,����ô���
	}
	public  void testMockObjectInvokeOrder() throws Exception 
	{
		//----
		IMocksControl control = EasyMock.createStrictControl();
		ResultSet mockResultSet = control.createMock(ResultSet.class);//������÷�����˳��
		//----
		for(int i=0;i<3;i++)
		{
			mockResultSet.next();
			EasyMock.expectLastCall().andReturn(true).times(1);//��next������1�η���true
			
			mockResultSet.getString(1);//��getString(1)�������õķ���
			EasyMock.expectLastCall().andReturn("DEMO_ORDER_00"+i).times(1);

			mockResultSet.getDouble(2);
			EasyMock.expectLastCall().andReturn(350.0 *(Math.pow(10,i)) ).times(1);
		}
		mockResultSet.next();
		EasyMock.expectLastCall().andReturn(false).times(1);//�ٵ�һ�η���false
		
		
		control.replay();//���ú�mockResultSet����ֵ������
		
		  int i = 0;
	      String[] priceLevels = { "Level_A", "Level_B", "Level_C" };
	      while (mockResultSet.next()) 
	      {
	        SalesOrder order = new SalesOrderImpl();
	        order.loadDataFromDB(mockResultSet);
	        Assert.assertEquals(order.getPriceLevel(), priceLevels[i]);
	        i++;
	      }
	  control.verify();//��֤����������������,����ô���
	}
	
	
	
	private String sqlEquals(String in)
	{
		EasyMock.reportMatcher(new SQLEquals(in));//ע���Զ���,implements IArgumentMatcher ,ʵ��matches����
		return in;
	}
	public  void  MockObjectMain() throws Exception 
	{
		ResultSet mockResultSet = EasyMock.createMock(ResultSet.class);//ֻ��Ҫһ�� Mock ����,����˳���ǲ����м��
		
		IMocksControl control = EasyMock.createControl();//ʹ�ö�� Mock ����
		java.sql.Connection mockConnection = control.createMock(Connection.class);//����˳���ǲ����м��
		java.sql.Statement mockStatement = control.createMock(Statement.class);
		// Mock ���󽫻ᾭ������״̬��Record ״̬�� Replay ״̬
		//---Mock ����һ������������״̬�ͱ���Ϊ Record
		
		mockResultSet.getString(1);
		EasyMock.expectLastCall().andReturn("My return value");//Ԥ�ڷ���ֵ
		
		mockStatement.executeQuery("SELECT * FROM sales_order_table");//�д�Сд������
		mockStatement.executeQuery( EasyMock.anyObject().toString() );
		mockStatement.executeQuery( sqlEquals("SELECT * FROM sales_order_table"));
		
		EasyMock.expectLastCall().andStubReturn(mockResultSet);//�����ĵ������Ƿ���һ����ͬ��ֵ
		SQLException exception=new SQLException();
		EasyMock.expectLastCall().andThrow(exception);//Ԥ���쳣�׳�
		EasyMock.expectLastCall().andStubThrow(exception);//�趨�׳�Ĭ���쳣
		
		mockResultSet.getString(1);
		EasyMock.expectLastCall().andReturn("My return value").times(3);//Ԥ�ڷ������ô���
		
		mockResultSet.close();
		EasyMock.expectLastCall().times(3, 5);//3-5�� atLeastOnce(),anyTimes()
		//---Replay ״̬
		EasyMock.replay(mockResultSet);
		control.replay();
		//...
		
		//Ϊ�˱������ɹ���� Mock ����EasyMock �����ԭ�� Mock �����������,��Ҫ���³�ʼ�������ǿ��Բ��� reset ����
		EasyMock.reset(mockResultSet);
		control.reset();
		
		EasyMock.verify(mockResultSet);
		control.verify();
		
		{
			ResultSet strickMockResultSet = EasyMock.createStrictMock(ResultSet.class);//������÷�����˳��
			
			IMocksControl strictControl = EasyMock.createStrictControl();
			ResultSet strickMockResultSet1 = strictControl.createMock(ResultSet.class);//������÷�����˳��
		}
	}
	@Test
	public  void  testCommonReturn() throws Exception 
	{
		IMocksControl mocksControl = EasyMock.createControl();
		SalesOrderImpl mockObject=mocksControl.createMock(SalesOrderImpl.class);
		
		
		
		//ƥ��ָ������
		mockObject.getProductPrice("tomota");
		EasyMock.expectLastCall().andStubReturn(45.0f);
	
		//ƥ�����в��� �������  OK
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
