package quiz;

import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

enum ColorEnum {red,blue};

public class My {

	public My()
	{
		System.out.println("PP");
		AtomicInteger x;
	}
	static class You extends My{
		public You()
		{
			System.out.println("CC");
		}
	}
	public static void main(String[] args) {
		new My(); //只输出PP
		
//		int a=0,b=0;
//		b=(a=2*6;3;5;8); //Java 没有逗号表达式
//		System.out.println(a);
		
	 	StringBuffer a=new StringBuffer("a");
    	StringBuffer b=new StringBuffer("b");
    	System.out.println(a+":");//可以,因有一个String
    	//System.out.println(a+b);//不可以
    	
    	
    	
    	
    	double rand=Math.random();//0-1
    	
    	long r=Math.round(11.2);//11
    	r=Math.round(11.5);//12
    	r=Math.round(-11.2);//-11
    	r=Math.round(-11.5);//-11
    	r=0;
		
		int i;
//		if(i=3)//不会有一个=的情况
//		if(new Object())//也不会有这种情况
		{
			System.out.println("hello");
		}
    	
		 function(5); //, output should be 0,0,1,2,3,4
    	
    	int searchVal=Arrays.binarySearch(new int[]{10,15,20,34,23},20);//数组要求排序的,二分查找法
    	System.out.println(searchVal);
    	
    	
    	
    	Integer i1= 127 ;
    	Integer i2= 127 ;
    	System.out.println(i1==i2);//不等,不会拆
    	
    	
    	Integer i3= 128; 
    	Integer i4= 128;
    	System.out.println(i3==i4);  //false ???  大坑
    	//对于Integer var=?在-128至127之间的赋值，Integer对象是在IntegerCache.cache产生，会复用已有对象，这个区间内的Integer值可以直接使用==进行判断，但是这个区间之外的所有数据，都会在堆上产生
    	
    	Integer i5= new Integer(100); 
    	Integer i6= new Integer(100); ;
    	System.out.println(i5==i6); //false ，是new就不同
    	
    	
    	//Arrays.sort(a);
    	//Collections.synchronizedMap(m); //所有方法用一个锁
    	Hashtable t;//方法加 synchronize,同所有方法用一个锁
    	ConcurrentHashMap<String,String> cm=new ConcurrentHashMap<>() ;
    	cm.put("one", "一");
    	
    	ConcurrentSkipListMap  clm;
    	LinkedHashMap m;
    	
    	//char x='李';
    	//byte x=30;
//    	short x=30;
//    	int x=30;
//    	String x="小小";
    	ColorEnum x=ColorEnum.red;
    	switch (x)//可char,byte ,int ,short,enum ,String是新版本的,不可用于long和小数
    	{
    	
    	}
	}	
	final class A//不能同时存在final和abstract
	{
		public void add(int a,int b)
		{
			
		}
		public void add(int a)
		{
			
		}
		public String  add(String a,String b)
		{
			return "";
		}
	}
	

	/*
	// StringBuffer 是线程安全的
	// StringBuilder 是线不程安全的
	 HashTable安全,ConcurrentHashMap安全,HashMap不安全
	 
	 Vectory安全,ArrayList不安全
	 LinkedList不安全,使用Collections.synchronizedList 
	 
	 Queue 有头的双向链表
	 Deque	at both ends,有头尾的双向链表
	 ConcurrentLinkedDeque
	 ConcurrentLinkedQueue
	 
	 SIT (System Integrate Test)
	 UAT (User Access Test)
	*/
	
	

public static void function(int i){
    if(i > 0){
        function(--i);
    }
    System.out.println(i);
}

	
}

