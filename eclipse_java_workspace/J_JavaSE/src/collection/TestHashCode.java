package collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeSet;

import classloader.MyObject;

public class TestHashCode {
	public static void main(String[] args) 
	{

		/*
		 Hashtable table=new Hashtable();
		table.put(null, "emp");//Hashtable 不可以加null为key 
		
		hash的原理
		JVM 拉圾回收的算法
		内存原理
		*/
		
		
		HashMap<MyObject,String> hash=new HashMap<MyObject,String>();
		MyObject one=new MyObject(1,"lisi");
		hash.put(one,"lisi 's value");
		hash.put(new MyObject(2,"zhang"),"zhang 's value");
		hash.put(null, "empty");
		
		MyObject key=new MyObject(1,"lisi") ;
		
		System.out.println( one.hashCode()==key.hashCode() );//true
		System.out.println( one ==key  ); //false
		System.out.println( one.equals(key)  );//false
		//必须同时重写 equals,hashCode
		
		System.out.println( hash.get(key ) );//null,原因没有重写equals
		System.out.println( hash.get(one ) );//lisi 's value
		
		
	}

}
