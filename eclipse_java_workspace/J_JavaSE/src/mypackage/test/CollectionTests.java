package mypackage.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionTests
{
    public static void main(String[] args)
    {
        List<String> lists=new ArrayList<String>();
        for(int i=0;i<10;i++)
        {
            lists.add("str"+i);
        }
        for(String str:lists)
        {
            if(str.equals("str3"))
            {
               // lists.remove(str); //这报错,不能在遍历的时候删List,是使用Iterator做的
            }
        }
        
        int len=lists.size();
        for(int i=0;i<len;i++ )
        {
            if(lists.get(i).equals("str3"))
            {
                lists.remove(i);
                i--;
                len--;
            }
        }
        
        System.out.println(lists);
        
        CopyOnWriteArrayList<String> changeList=new CopyOnWriteArrayList<String>(lists);
        for(String str:changeList)
        {
            if(str.equals("str3"))
            {
               changeList.remove(str);  
            }
        }
        System.out.println(changeList);
    }
}
