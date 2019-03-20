package not_web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.boot.system.JavaVersion;
import org.springframework.cache.CacheManager;

import mybatis.dao.UserMapper;

//Unit Test use

//@ConditionalOnBean(RedisTemplate.class)//存在某个Bean时
//@ConditionalOnMissingBean(CacheManager.class) 
//@ConditionalOnJava(range=ConditionalOnJava.Range.EQUAL_OR_NEWER,value=JavaVersion.NINE)//当Java版本>=9.0

@ConditionalOnProperty(name="useMyprop",havingValue="yes",matchIfMissing=true)//如没有配置，就是havingValue配置的值yes
@Component
@ConfigurationProperties(prefix="my-props") //接收application.yml中的myProps下面的属性
public class MyProps {
	private String simpleProp;
	private String[] arrayProps;
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
	private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
	private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
	
	public String getSimpleProp() {
		return simpleProp;
	}
	
	//String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要
	public void setSimpleProp(String simpleProp) {
		this.simpleProp = simpleProp;
	}
	
	public List<Map<String, String>> getListProp1() {
		return listProp1;
	}
	public List<String> getListProp2() {
		return listProp2;
	}
 
	public String[] getArrayProps() {
		return arrayProps;
	}
 
	public void setArrayProps(String[] arrayProps) {
		this.arrayProps = arrayProps;
	}
 
	public Map<String, String> getMapProps() {
		return mapProps;
	}
 
	public void setMapProps(Map<String, String> mapProps) {
		this.mapProps = mapProps;
	}
}