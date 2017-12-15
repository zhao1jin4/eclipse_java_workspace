package not_test;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

public class ExamplePlugin implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("before===========");
		Object o= invocation.proceed();
		System.out.println("after===========");
		return o;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}
}
