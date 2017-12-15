package spring_config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class User 
{
	Home home;
	private int age;
	private Set<String> sets=new HashSet<String>();
	private List<String> lists=new ArrayList<String>();
	private String[] arrays=new String[10];
	private Map<String,String> maps=new HashMap<String,String>();
	private Properties props=new Properties();

public User() {
}

public Home getHome() {
	return home;
}

public void setHome(Home home) {
	this.home = home;
}

public User(int age) {
	this.age = age;
}
public String[] getArrays() {
	return arrays;
}

public void setArrays(String[] arrays) {
	this.arrays = arrays;
}

public Set<String> getSets() {
	return sets;
}

public void setSets(Set<String> sets) {
	this.sets = sets;
}

public List<String> getLists() {
	return lists;
}

public void setLists(List<String> lists) {
	this.lists = lists;
}

public Map<String, String> getMaps() {
	return maps;
}

public void setMaps(Map<String, String> maps) {
	this.maps = maps;
}

public Properties getProps() {
	return props;
}

public void setProps(Properties props) {
	this.props = props;
}

public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
}
