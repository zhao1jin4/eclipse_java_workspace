package org.zhaojin;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MainPropertiesTest
{
	public static void main(String args[])
	{
		Properties p=System.getProperties();
		Set set=p.entrySet();
		Iterator i=set.iterator();
		while(i.hasNext())
		{
			Map.Entry entry=(Map.Entry)i.next();
			if(entry.getValue().equals("zhaojin"))
			System.out.println(entry.getKey()+":==:"+entry.getValue());
		}
	}
}
