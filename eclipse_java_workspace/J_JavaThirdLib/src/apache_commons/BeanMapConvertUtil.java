package apache_commons;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanMapConvertUtil {


	public static void transMap2Bean(Map<String, Object> map, Object obj) throws Exception// Map->Bean
	{
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (map.containsKey(key) && !"class".equals(key)) { //ת����map��class����,keyҲ��
				Object value = map.get(key);
				Method setter = property.getWriteMethod(); // �õ�property��Ӧ��setter����
				setter.invoke(obj, value);
			}
		}
		return;
	}

	public static Map<String, Object> transBean2Map(Object obj) throws Exception // Bean->Map
	{
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());// java.beans.Introspector
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			Method getter = property.getReadMethod(); // �õ�property��Ӧ��getter����
			Object value = getter.invoke(obj);
			map.put(key, value);
		}
		return map;
	}
}
