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
		//老的Set正在Iterator时,不能使用Collection的remove,add,但可以用Iterator的remove
		//Collection all=new ArrayList  ();
		Collection all=new CopyOnWriteArrayList();
		all.add(new User("张三"));
		all.add(new User("李四"));
		all.add(new User("王五"));
		Iterator  iter=all.iterator();
		while(iter.hasNext())
		{
			User item=(User)iter.next();
			if(item.name.equals("张三"))
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
		//SynchronousQueue 是一个阻塞队列,每次的插入必须有取时才会插入,否则等有人来取,可以有多个来取
 
		
		 
		
		//查Queue,BlockingQueue的API
		//Queue队列满时,add    方法抛出异常,offer(可传超时时间)方法返回 false   ,put 方法会阻塞	,Insert操作
		//Queue队列空时,remove 方法抛出异常 ,poll(可传超时时间)方法返回 null	   ,take方法会阻塞	, Remove操作
		//Queue队列空时,element方法抛出异常 ,peek 			   方法返回 null   			 	,examine操作
		
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
		
		
	
		
		//BlockingQueue blockQueue=new ArrayBlockingQueue(2);//有容量限制
		BlockingQueue<String> blockQueue=new PriorityBlockingQueue<>();//PriorityBlockingQueue按自然排序或者传Comparator
		try {
			blockQueue.put("one");
			blockQueue.put("two");
			blockQueue.put("three");
			blockQueue.put("four");
			//BlockingQueue put如果队列满会阻塞,可以用于生产者和消费者
			//BlockingQueue take如果队列空会阻塞
			Object obj=blockQueue.take();
			
			//DelayQueue队列中的元素必须实现新的 Delayed 接口
			//添加可以立即返回，但是在延迟时间过去之前，不能从队列中取出元素。如果多个元素完成了延迟，那么最早失效(失效时间最长)的元素将第一个取出,不可放null, size返回所有(过期和未过期的)
			
			//SynchronousQueue 阻塞的
			//ConcurrentMap 没有put方法,有putIfAbsent,replace方法,
			ConcurrentHashMap<String,String> map=null;
			
			
			//CopyOnWriteArrayList,CopyOnWriteArraySet
			// copy-on-write 模式,首先取得后台数组的副本，对副本进行更改，然后替换副本
			//保证了在遍历自身更改的集合时，永远不会抛出,遍历集合会用原来的集合完成，而在以后的操作中使用更新后的集合。 
			//最适合于读操作通常大大超过写操作的情况
			
			String strings[]=new String[]{"test","one"};
		    List<String> list1 = new CopyOnWriteArrayList<>(Arrays.asList(strings));
		    List<String> list2 = new ArrayList<>(Arrays.asList(strings));
		    Iterator<String> itor1 = list1.iterator();
		    Iterator<String> itor2 = list2.iterator();
		    list1.add("New");
		    list2.add("New");//在iterator后调用add,遍历就会出错,而CopyOnWriteArrayList就会使用之前的
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
