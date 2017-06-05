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
			System.out.print ("\n value="+cloneRoot.value);//��дtoString
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
				Node existNode=exisNextNode(cloneRoot,inRootNode.randomNext.value);// ��������������������ͬ,�ͻ�������
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
					 map.put(inRootNode.randomNext.value,current.randomNext);//��������������������ͬ,�ͻ�������
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
˼·��ԭ��ÿ���ڵ�֮������µĽڵ�,�γ�һ��2���ڵ���������,(�µ�randomͬԴ��randomָ��,���п���)
�ڶ���while���µ�random ָ��Դ��random.next
������ while �ָ�ԭ����(nextָ��), ������(nextָ��)

RandomListNode *copyRandomList(RandomListNode *head) {  
        // write your code here  
        RandomListNode *p = head;  
        RandomListNode *dest, *t = NULL;  
        while (p != NULL) {  
            t = new RandomListNode(p->label);  
            t->next = p->next;  
            t->random = p->random;  
            p->next = t;  
            p = t->next; //ȡ��Դ�����е���һ�����  
        }  
          
        p = head;  
        while (p != NULL) {  
            t = p->next;  
            if (t->random != NULL) {  // �˴���Ҫ�ж�Դ�ڵ��random�Ƿ�Ϊ�գ������Ϊ�ղ���Ҫ����  
                t->random = t->random->next;  
            }  
            p = t->next;  
        }  
        p = head;  
        dest = p->next;  
        while (p != NULL) {  
            t = p->next;  
            p->next = t->next; //�¾��������ľ�(Դ)����  
            p = t->next;  
            if (p != NULL) {  
                t->next = p->next;  //�¾���������������  
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
			t.randomNext=p.randomNext;//���п���
			p.next=t;
			p=t.next;
		}
		
		
		p = rootNode;  
        while (p != null) {  
            t = p.next;  
            if (t.randomNext != null) {  // �˴���Ҫ�ж�Դ�ڵ��random�Ƿ�Ϊ�գ������Ϊ�ղ���Ҫ����  
                t.randomNext = t.randomNext.next;  
            }  
            p = t.next;  
        }  
	        
	        
	        
        p = rootNode;  
       Node dest = p.next;  
        while (p != null) {  
            t = p.next;  
            p.next = t.next; //�¾��������ľ�(Դ)����  
            p = t.next;  
            if (p != null) {  
                t.next = p.next;  //�¾���������������  
            }  
        }  
        return dest;
	}
}
class Node implements Cloneable
{
	String value;//ֻ���ֵ���Ը���,  ����Ϊ��
	Node next; //��������,Ϊ�ձ�ʾ��ֹ
	Node randomNext;//���ָ��,������ѵ�
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