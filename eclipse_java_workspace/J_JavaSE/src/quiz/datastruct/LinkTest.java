package quiz.datastruct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class LinkTest {

	public static void main(String[] args) {
		Node rootNode=new Node();
		rootNode.value="root";
		
		Node oneNode=new Node();
		oneNode.value="one";
		
		Node twoNode=new Node();
		twoNode.value="two";
		
		rootNode.next=oneNode;
		rootNode.randomNext=twoNode;
		
		oneNode.next=twoNode;
		oneNode.randomNext=null;
		
		twoNode.next=null;
		oneNode.randomNext=null;
		
//		Node cloneRoot=deepCopy(rootNode);
//		Node cloneRoot=deepCopy2(rootNode);
		
		Node cloneRoot=deepCopyNet(rootNode);
		
		List<Node> arrayList =new ArrayList<Node> ();
		while(cloneRoot!=null  )
		{
			System.out.print ("\n value="+cloneRoot.value);//重写toString
			System.out.print (",next="+cloneRoot.next);
			System.out.print (",randomNext="+cloneRoot.randomNext);
			
			System.out.print (",this.hashCode="+cloneRoot.hashCode());
			if(cloneRoot.next!=null)
				System.out.print (",next.hashCode="+cloneRoot.next.hashCode());
			if(cloneRoot.randomNext!=null)
				System.out.print (",randomNext.hashCode="+cloneRoot.randomNext.hashCode());
			
			if(!arrayList.contains(cloneRoot))
				arrayList.add(cloneRoot);
			
			if(cloneRoot.randomNext!=null)
			{
				if(!arrayList.contains(cloneRoot.randomNext))
					arrayList.add(cloneRoot.randomNext);
			}
			
			cloneRoot=cloneRoot.next;
		}
		System.out.println (arrayList);
		
		LinkedList list;
		Collections x;
		Deque deue;
		Queue queue;
		Arrays y;
	}
	public static  Node exisNextNode(Node rootNode, String value)
	{
		while(rootNode!=null)
		{
			if(rootNode.value== value)
				return rootNode;
			rootNode=rootNode.next;
		}
		return null;
	}
	public static  Node existRandomNode(Node rootNode, String value)
	{
		while(rootNode!=null)
		{
			if(rootNode.randomNext!=null && rootNode.randomNext.value==value)
				return rootNode.randomNext;
			
			rootNode=rootNode.next;
		}
		return null;
	}
	
	public static  Node deepCopy(Node inRootNode)
	{
		Node cloneRoot=new Node();
		Node current=cloneRoot;
		
		while(inRootNode!=null)//
		{
			//--
			current.value=inRootNode.value;
			
			if(inRootNode.randomNext!=null)
			{
				Node existNode=exisNextNode(cloneRoot,inRootNode.randomNext.value);// 但如果有两个结点数据相同,就会有问题
				if(existNode!=null)
					current.randomNext=existNode;
				 else
					current.randomNext=new Node(inRootNode.randomNext.value);
			}
			
			if(inRootNode.next!=null)
			{
				Node existRandomNode=existRandomNode(cloneRoot,inRootNode.next.value);
				if(existRandomNode!=null)
					current.next=existRandomNode;
				 else
					//current.next=new Node(inRootNode.randomNext.value);
					current.next=new Node();
			}
			current=current.next;
			
			//---	
			inRootNode=inRootNode.next;
		}
		
		
		return cloneRoot;
	}
	public static  Node deepCopy2(Node inRootNode)
	{
		Node cloneRoot=new Node();
		Node current=cloneRoot;
		
		Map<String,Node> map =new HashMap<>();
		while(inRootNode!=null)//
		{
			//--
			current.value=inRootNode.value;
			
			if(inRootNode.randomNext!=null)
			{
				Node existNode=map.get(inRootNode.randomNext.value);
				if(existNode!=null)
					current.randomNext=existNode;
				 else
				 {
					 current.randomNext=new Node(inRootNode.randomNext.value);
					 map.put(inRootNode.randomNext.value,current.randomNext);//但如果有两个结点数据相同,就会有问题
				 }
			}
			if(inRootNode.next!=null)
			{
				Node existNode=map.get(inRootNode.next.value);
				if(existNode!=null)
					current.next=existNode;
				 else
				 {
					 current.next=new Node( );
					//current.next=new Node(inRootNode.randomNext.value);
					 map.put(inRootNode.next.value,current.next);
				 }
			}
			
		 
			current=current.next;
			
			//---	
			inRootNode=inRootNode.next;
		}
		
		
		return cloneRoot;
		  
	}
	
	/*
思路在原有每个节点之后插入新的节点,形成一个2倍节点数的链表,(新的random同源的random指向,可有可无)
第二次while把新的random 指向源的random.next
第三次 while 恢复原链表(next指向), 新链表(next指向)

RandomListNode *copyRandomList(RandomListNode *head) {  
        // write your code here  
        RandomListNode *p = head;  
        RandomListNode *dest, *t = NULL;  
        while (p != NULL) {  
            t = new RandomListNode(p->label);  
            t->next = p->next;  
            t->random = p->random;  
            p->next = t;  
            p = t->next; //取得源链表中的下一个结点  
        }  
          
        p = head;  
        while (p != NULL) {  
            t = p->next;  
            if (t->random != NULL) {  // 此处需要判断源节点的random是否为空，如果不为空才需要更新  
                t->random = t->random->next;  
            }  
            p = t->next;  
        }  
        p = head;  
        dest = p->next;  
        while (p != NULL) {  
            t = p->next;  
            p->next = t->next; //新旧链表分离的旧(源)链表  
            p = t->next;  
            if (p != NULL) {  
                t->next = p->next;  //新旧链表分离的新链表  
            }  
        }  
        return dest;  
    }  
	
	 */
	
	public static  Node deepCopyNet(Node rootNode)
	{
	 
		Node p=rootNode;
		Node t=null;
		while(p!=null)
		{
			t=new Node(p.value);
			t.next=p.next;
			t.randomNext=p.randomNext;//可有可无
			p.next=t;
			p=t.next;
		}
		
		
		p = rootNode;  
        while (p != null) {  
            t = p.next;  
            if (t.randomNext != null) {  // 此处需要判断源节点的random是否为空，如果不为空才需要更新  
                t.randomNext = t.randomNext.next;  
            }  
            p = t.next;  
        }  
	        
	        
	        
        p = rootNode;  
       Node dest = p.next;  
        while (p != null) {  
            t = p.next;  
            p.next = t.next; //新旧链表分离的旧(源)链表  
            p = t.next;  
            if (p != null) {  
                t.next = p.next;  //新旧链表分离的新链表  
            }  
        }  
        return dest;
	}
}
class Node implements Cloneable
{
	String value;//只这个值可以复制,  可以为空
	Node next; //单向链表,为空表示终止
	Node randomNext;//随机指向,这个是难点
	public Node( ) {
	}

	public Node(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
	
}