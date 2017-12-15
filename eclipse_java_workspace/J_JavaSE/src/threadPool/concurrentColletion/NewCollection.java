package threadPool.concurrentColletion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.PriorityBlockingQueue;

public class NewCollection {
	
	class User
	{
		String name;
		public User(String name){
			this.name=name;
		}
		@Override
		public boolean equals(Object obj) {
			if(obj!=null && obj instanceof User)
			{
				return ((User)obj).name.equals(this.name);
			}
			return false;
		}
		
	}
	public  void problemTest( ) 
	{
		//�ϵ�Set����Iteratorʱ,����ʹ��Collection��remove,add,��������Iterator��remove
		//Collection all=new ArrayList  ();
		Collection all=new CopyOnWriteArrayList();
		all.add(new User("����"));
		all.add(new User("����"));
		all.add(new User("����"));
		Iterator  iter=all.iterator();
		while(iter.hasNext())
		{
			User item=(User)iter.next();
			if(item.name.equals("����"))
			{
				all.remove(item);
			}
			else
				System.out.println(item.name);
		}
	}
		
	public static void main(String[] args) 
	{
		new NewCollection().problemTest();
		//SynchronousQueue ��һ����������,ÿ�εĲ��������ȡʱ�Ż����,�����������ȡ,�����ж����ȡ
 
		
		 
		
		//��Queue,BlockingQueue��API
		//Queue������ʱ,add    �����׳��쳣,offer(�ɴ���ʱʱ��)�������� false   ,put ����������	,Insert����
		//Queue���п�ʱ,remove �����׳��쳣 ,poll(�ɴ���ʱʱ��)�������� null	   ,take����������	, Remove����
		//Queue���п�ʱ,element�����׳��쳣 ,peek 			   �������� null   			 	,examine����
		
		Queue<String> queue=new LinkedList<>();
		  queue.offer("One");
		  queue.offer("Two");
		  queue.offer("Three");
		  queue.offer("Four");
		System.out.println("size is: " + queue.size());
		System.out.println("Head of queue is: " + queue.poll()); 
		System.out.println("Head of queue is: " + queue.remove());
		System.out.println("size is: " + queue.size());
//		System.out.println("Head of queue is: " + queue.peek()); 
//		System.out.println("Head of queue is: " + queue.element());
		
		
	
		
		//BlockingQueue blockQueue=new ArrayBlockingQueue(2);//����������
		BlockingQueue<String> blockQueue=new PriorityBlockingQueue<>();//PriorityBlockingQueue����Ȼ������ߴ�Comparator
		try {
			blockQueue.put("one");
			blockQueue.put("two");
			blockQueue.put("three");
			blockQueue.put("four");
			//BlockingQueue put���������������,�������������ߺ�������
			//BlockingQueue take������пջ�����
			Object obj=blockQueue.take();
			
			//DelayQueue�����е�Ԫ�ر���ʵ���µ� Delayed �ӿ�
			//��ӿ����������أ��������ӳ�ʱ���ȥ֮ǰ�����ܴӶ�����ȡ��Ԫ�ء�������Ԫ��������ӳ٣���ô����ʧЧ(ʧЧʱ���)��Ԫ�ؽ���һ��ȡ��,���ɷ�null, size��������(���ں�δ���ڵ�)
			
			//SynchronousQueue ������
			//ConcurrentMap û��put����,��putIfAbsent,replace����,
			ConcurrentHashMap<String,String> map=null;
			
			
			//CopyOnWriteArrayList,CopyOnWriteArraySet
			// copy-on-write ģʽ,����ȡ�ú�̨����ĸ������Ը������и��ģ�Ȼ���滻����
			//��֤���ڱ���������ĵļ���ʱ����Զ�����׳�,�������ϻ���ԭ���ļ�����ɣ������Ժ�Ĳ�����ʹ�ø��º�ļ��ϡ� 
			//���ʺ��ڶ�����ͨ����󳬹�д���������
			
			String strings[]=new String[]{"test","one"};
		    List<String> list1 = new CopyOnWriteArrayList<>(Arrays.asList(strings));
		    List<String> list2 = new ArrayList<>(Arrays.asList(strings));
		    Iterator<String> itor1 = list1.iterator();
		    Iterator<String> itor2 = list2.iterator();
		    list1.add("New");
		    list2.add("New");//��iterator�����add,�����ͻ����,��CopyOnWriteArrayList�ͻ�ʹ��֮ǰ��
		    try {
		      printAll(itor1);
		    } catch (ConcurrentModificationException e) {
		      System.err.println("CopyOnWriteArrayList  Error");
		    }
		    try {
		      printAll(itor2);
		    } catch (ConcurrentModificationException e) {
		      System.err.println("ArrayList Error");
		    }
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	 
		
	 private static void printAll(Iterator<String> itor)
	 {
		    while (itor.hasNext()) {
		      System.out.println(itor.next());
		    }
	}
	
}
