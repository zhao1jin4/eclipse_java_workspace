package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {

	public static void main(String[] args) {
		 List<String> list=new ArrayList<String>();
		 for(int i=0;i<8;i++)
		 {
			 list.add("str"+i);
		 }
		 
//		 for(int i=0;i<list.size();i++)
//		 {
//			 if(list.get(i).equals("str3"))
//			 {
//				 list.remove(i);
//				if(i>0)
//					i--;
//			 }
//		 }
		 
		 int i=0;
		 for(Iterator<String> iter=list.iterator();iter.hasNext();)
		 {
			 String str=iter.next();
			 i++;
			 if(str.equals("str3"))
				 {
					//list.remove(i);//iteratorʱ����ɾ,����
				   //list.add("new_node");//Ҳ��������Ԫ��
				 
				 	//iter.remove();//��ɾ�����е�����
				 }
		 }
		 System.out.println(list);
	}

}
