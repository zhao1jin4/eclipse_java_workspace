package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsTest
{
	public static void main(String[] args)
	{
		
		java.util.Hashtable<String,String>  t =new java.util.Hashtable<String,String> ();
//		t.put("key",null);   //key ,value 都不能为null
		//HashTable key没有!=null的判断取hashCode,value有判断
		//HashMap key 为何能为空 ，因如有判断如为null hashcode就为0,hashtable key为什么不能为空
		
		int data[]=new int[]{1,4,3,9,7,5,2};
	
		
		List<Integer> arrayList=new ArrayList<Integer>();
		Collections.addAll(arrayList, data[0],data[1],data[2]);
		Collections.sort(arrayList);
		System.out.println("colletion sort:"+arrayList);
		
		
		
		List<Integer> myList=Arrays.asList(data[0],data[1],data[2]);
		System.out.println(myList);
		Collections.rotate(myList, 1);
		System.out.println("rotate:"+myList);
		
		Collections.reverse(myList);
		System.out.println("reverse:"+myList);
		
		Arrays.sort(data);
		System.out.println("sort:"+Arrays.toString(data));
		
		
		List<Integer> rotateList=Arrays.asList(3,5,8,9,1,0);
		System.out.println("befor rotate:"+  rotateList );
		Collections.rotate(rotateList, 2);//把数组 最后2位 放在 最前面
		System.out.println("after rotate:"+  rotateList );
		
		
		Vector<String> vector=new Vector<String>(); //如构造时不传扩容数,默认是一倍,实现是建新的数组,再复制过去
    	vector.add("first");
    	vector.add("second");
    	Enumeration<String> enumer= vector.elements();
    	while(enumer.hasMoreElements())
    	{
    		vector.remove("second");
    		System.out.println(enumer.nextElement());//可以删
    	}
    	
    	vector.iterator();//不能加,删
    	
    	ArrayList list=new ArrayList();//扩容
    	
    	
    	TreeMap treeMap;
    	ConcurrentHashMap conHashMap;
	}

}
