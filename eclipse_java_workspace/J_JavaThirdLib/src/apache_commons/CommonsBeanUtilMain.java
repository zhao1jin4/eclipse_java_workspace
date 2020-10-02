package apache_commons;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

public class CommonsBeanUtilMain {
	 
	public static void main(String[] args) throws Exception  {
		//    1.9.3 最新版本
		 
		 UserVO user=new UserVO();
		 user.setBirthday(new Date());
//		 user.setCreateTime(new Timestamp(new Date().getTime()));
		 user.setId(100);
		 user.setName("王");
		 user.setSalary(10000.80);
		 
		 Map<String, Object> mapRes= BeanMapConvertUtil.transBean2Map(user);
		 UserVO userRes=new UserVO();
		 BeanMapConvertUtil.transMap2Bean(mapRes, userRes);
		 
		 UserVO dest=new UserVO();
		 UserVO orig=user;
	 
		 //有日期不行？？？
		 BeanUtils.copyProperties(dest, orig); // commons.beanutils 和 spring都有
		 
//		 ConvertUtils.register(new  SqlDateConverter(null), java.sql.Date.class);
//         ConvertUtils.register(new  SqlDateConverter(null), java.util.Date.class);
//         ConvertUtils.register(new  SqlTimestampConverter(null),  java.sql.Timestamp.class);
//		
		 
	ConvertUtilsBean convert=new ConvertUtilsBean();
	convert.register(new DateConverter(), Date.class); 
	convert.register(new SqlTimestampConverter(), Timestamp.class);
	 BeanUtilsBean utilBean=new BeanUtilsBean(convert,new PropertyUtilsBean());
	
	 //----no use
		 Map res=utilBean.describe(user);
		System.out.println(res);
		
		
		
		//----no use
		Map<String,Object> map=new HashMap<>();
		map.put("id", 200);
		map.put("salary", 300.20);
		map.put("birthday", new Date());
		map.put("name", "wang"); 
		
		 UserVO user1=new UserVO();
		 BeanUtils.populate(user1, map);//
		 System.out.println(user1.getName());
		 System.out.println(user1.getBirthday());
	 
	}
}
class UserVO
{
	private long id;
	private double salary;
	private String name;
	private Date birthday;
	//private Timestamp createTime;
//	public Timestamp getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Timestamp createTime) {
//		this.createTime = createTime;
//	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	
	
}