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
		new My(); //ֻ���PP
		
//		int a=0,b=0;
//		b=(a=2*6;3;5;8); //Java û�ж��ű��ʽ
//		System.out.println(a);
		
	 	StringBuffer a=new StringBuffer("a");
    	StringBuffer b=new StringBuffer("b");
    	System.out.println(a+":");//����,����һ��String
    	//System.out.println(a+b);//������
    	
    	
    	
    	
    	double rand=Math.random();//0-1
    	
    	long r=Math.round(11.2);//11
    	r=Math.round(11.5);//12
    	r=Math.round(-11.2);//-11
    	r=Math.round(-11.5);//-11
    	r=0;
		
		int i;
//		if(i=3)//������һ��=�����
//		if(new Object())//Ҳ�������������
		{
			System.out.println("hello");
		}
    	
		 function(5); //, output should be 0,0,1,2,3,4
    	
    	int searchVal=Arrays.binarySearch(new int[]{10,15,20,34,23},20);//����Ҫ�������,���ֲ��ҷ�
    	System.out.println(searchVal);
    	
    	
    	
    	Integer i1= 127 ;
    	Integer i2= 127 ;
    	System.out.println(i1==i2);//����,�����
    	
    	
    	Integer i3= 128; 
    	Integer i4= 128;
    	System.out.println(i3==i4);  //false ???  ���
    	//����Integer var=?��-128��127֮��ĸ�ֵ��Integer��������IntegerCache.cache�������Ḵ�����ж�����������ڵ�Integerֵ����ֱ��ʹ��==�����жϣ������������֮����������ݣ������ڶ��ϲ���
    	
    	Integer i5= new Integer(100); 
    	Integer i6= new Integer(100); ;
    	System.out.println(i5==i6); //false ����new�Ͳ�ͬ
    	
    	
    	//Arrays.sort(a);
    	//Collections.synchronizedMap(m); //���з�����һ����
    	Hashtable t;//������ synchronize,ͬ���з�����һ����
    	ConcurrentHashMap<String,String> cm=new ConcurrentHashMap<>() ;
    	cm.put("one", "һ");
    	
    	ConcurrentSkipListMap  clm;
    	LinkedHashMap m;
    	
    	//char x='��';
    	//byte x=30;
//    	short x=30;
//    	int x=30;
//    	String x="СС";
    	ColorEnum x=ColorEnum.red;
    	switch (x)//��char,byte ,int ,short,enum ,String���°汾��,��������long��С��
    	{
    	
    	}
	}	
	final class A//����ͬʱ����final��abstract
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
	// StringBuffer ���̰߳�ȫ��
	// StringBuilder ���߲��̰�ȫ��
	 HashTable��ȫ,ConcurrentHashMap��ȫ,HashMap����ȫ
	 
	 Vectory��ȫ,ArrayList����ȫ
	 LinkedList����ȫ,ʹ��Collections.synchronizedList 
	 
	 Queue ��ͷ��˫������
	 Deque	at both ends,��ͷβ��˫������
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

