package apache_commons;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class CommonsPoolMain {
	 
	public static void main(String[] args) throws Exception {
		 
		//jedis使用这个
		GenericObjectPoolConfig<StringBuffer> config=new GenericObjectPoolConfig<StringBuffer>();
		config.setMaxTotal(20);
		config.setMaxIdle(10);
		config.setMinEvictableIdleTimeMillis(300000);
		config.setNumTestsPerEvictionRun(3);
		config.setTimeBetweenEvictionRunsMillis(60000);
		config.setTestOnBorrow(false);
		config.setBlockWhenExhausted(false);//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		//逐出连接的最小空闲时间 默认
		config.setMinEvictableIdleTimeMillis(20*60*1000);//20分
		
		
		GenericObjectPool<StringBuffer> pool=new GenericObjectPool<StringBuffer>(new StringBufferFactory(),config);
		
//		StringBuffer buf = pool.borrowObject();
//		//....
//		pool.returnObject(buf);
		
//		PooledObject<Connection> obj=new DefaultPooledObject<Connection>(conn);
//		Connection conn=obj.getObject();
//		obj.getObject().close();
		
		
		ReaderUtil readerUtil = new ReaderUtil(pool);
		
		try {
			String res=readerUtil.readToString(new StringReader("123"));
			System.out.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ConnectionFactory factory = new ConnectionFactory(); 
		factory.setUsername("zh"); 
		factory.setPassword("123"); 
		factory.setVirtualHost("/"); 
		factory.setHost("127.0.0.1"); 
		factory.setPort(AMQP.PROTOCOL.PORT); 
	        factory.setAutomaticRecoveryEnabled(true);
	        factory.setNetworkRecoveryInterval(3000);
//	        factory.setConnectionTimeout(5000);
//	        factory.setExceptionHandler(new CustomerExceptionHandler());
		MyPoolFactory myFactory=new MyPoolFactory(factory);
		
		
		try {
			PooledObject<Connection> obj=myFactory.makeObject();
			Connection conn=	obj.getObject();
			//use 
			myFactory.destroyObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class MyPoolFactory implements PooledObjectFactory<Connection> {
	ConnectionFactory factory;

	public MyPoolFactory(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public void destroyObject(PooledObject<Connection> obj) throws Exception {
		if (obj.getObject() != null)
			obj.getObject().close();
	}

	@Override
	public PooledObject<Connection> makeObject() throws Exception {
		Connection conn = factory.newConnection();
		return new DefaultPooledObject<Connection>(conn);
	}

	@Override
	public boolean validateObject(PooledObject<Connection> obj) {
		if (obj.getObject() != null && obj.getObject().isOpen())
			return true;
		return false;
	}

	@Override
	public void activateObject(PooledObject<Connection> obj) throws Exception {

	}

	@Override
	public void passivateObject(PooledObject<Connection> obj) throws Exception {

	}

}