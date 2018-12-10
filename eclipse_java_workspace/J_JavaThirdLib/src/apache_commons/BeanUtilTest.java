package apache_commons;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map; 

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

/**
 * 当把Person类作为BeanUtilTest的内部类时，程序出错<br>
 * java.lang.NoSuchMethodException: Property '**' has no setter method<br>
 * 本质：内部类 和 单独文件中的类的区别 <br>
 * BeanUtils.populate方法的限制：<br>
 * The class must be public, and provide a public constructor that accepts no arguments. <br>
 * This allows tools and applications to dynamically create new instances of your bean, <br>
 * without necessarily knowing what Java class name will be used ahead of time
 */
public class BeanUtilTest {

    public static void main(String[] args) throws Exception {

    	
    	  PersonBean dest = new PersonBean();
          UserBean orig =new UserBean();
          orig.setAge(20);
          orig.setBirthday(new Date());
          orig.setName("lisi");
          
  	      BeanUtils.copyProperties(dest, orig); // commons.beanutils 和 spring都有，双方不同类都有匹配不上的字段也可正常用
          //timestamp的任何一端也没有字段对应   也OK 
      
          System.out.println("--- Bean Info: "); 
          System.out.println("age: " + dest.getAge());
          System.out.println("mN: " + dest.getmN());//
          
//	    ConvertUtils.register(new  SqlDateConverter(null), java.sql.Date.class);
//        ConvertUtils.register(new  SqlDateConverter(null), java.util.Date.class);
//        ConvertUtils.register(new  SqlTimestampConverter(null),  java.sql.Timestamp.class);
    
    	 
    	 ConvertUtils.register(new Converter()
         {
              
             @Override
             public Object convert(Class arg0, Object arg1)
             {
                 if(arg1 == null)
                 {
                     return null;
                 }
                 if(!(arg1 instanceof String))
                 {
                     throw new ConversionException("只支持字符串转换 !");
                 }
                  
                 String str = (String)arg1;
                 if(str.trim().equals(""))
                 {
                     return null;
                 }
                  
                 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 try {
					return sd.parse(str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
                 return null;
             }
              
         }, java.util.Date.class);//map要只字串，可转成日期
         
        PersonBean person = new PersonBean();
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("name", "Mike");
        mp.put("age", 25);
        mp.put("mN", "male");
        mp.put("birthday", "2017-08-20 14:20:10");	
        BeanUtils.populate(person, mp); //org.apache.commons.beanutils
  

        // 将javaBean 转换为map
//        Map<String, Object> map = transBean2Map(person);
        Map<String, Object> map = BeanMapConvertUtil.transBean2Map(person);
        System.out.println("--- transBean2Map Map Info: ");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }        
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());//java.beans.Introspector
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

               
                if (!key.equals("class")) {  // 过滤class属性
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}


