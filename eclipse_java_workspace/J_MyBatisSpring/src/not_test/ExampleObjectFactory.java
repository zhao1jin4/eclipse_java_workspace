package not_test;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
//not do anything
public class ExampleObjectFactory extends DefaultObjectFactory {
	public Object create(Class type) {
		return super.create(type);
	}

	public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes,
			List<Object> constructorArgs) {
		return super.create(type, constructorArgTypes, constructorArgs);
	}

	public void setProperties(Properties properties) {
		super.setProperties(properties);
	}
}