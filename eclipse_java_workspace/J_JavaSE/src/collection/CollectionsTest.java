package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsTest
{
	public static void main(String[] args)
	{
		
		java.util.Hashtable<String,String>  t =new java.util.Hashtable<String,String> ();
//		t.put("key",null);   //key ,value ������Ϊnull
		//HashTable keyû��!=null���ж�ȡhashCode,value���ж�
		//HashMap key Ϊ����Ϊ�� ���������ж���Ϊnull hashcode��Ϊ0,hashtable keyΪʲô����Ϊ��
		
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
		Collections.rotate(rotateList, 2);//������ ���2λ ���� ��ǰ��
		System.out.println("after rotate:"+  rotateList );
		
		
		Vector<String> vector=new Vector<String>(); //�繹��ʱ����������,Ĭ����һ��,ʵ���ǽ��µ�����,�ٸ��ƹ�ȥ
    	vector.add("first");
    	vector.add("second");
    	vector.add("three");
    	Enumeration<String> enumer= vector.elements();
    	while(enumer.hasMoreElements())
    	{
    		vector.remove("second");//Enumeration vector ����ɾ
    		System.out.println(enumer.nextElement());
    		//Enumerationû ��remove����
    	}
    
    	Iterator<String> iterator=vector.iterator();
    	while(iterator.hasNext())
    	{
    		String val=iterator.next();
    		if(val.contentEquals("three"))
    		{
//        		vector.remove("three");//iterator vector ������ɾ
//    			vector.add("four");//iterator vector �����Լ�
    			iterator.remove();//iterator����ɾ
    		}
    		System.out.println(val);
    	}
    	
    	
    	
    	ArrayList list=new ArrayList();//����
    	
    	
    	TreeMap treeMap;
    	ConcurrentHashMap conHashMap=new ConcurrentHashMap() ;
    	conHashMap.put("key", "value");
    	
    	int hash= (123451337 ^ (123451337 >>> 16)) & 0x7fffffff;
    	System.out.println(hash);
    	
    	return ;
	}

}
