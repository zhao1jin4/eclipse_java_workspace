package mypackage.test;

import java.beans.Introspector;
import java.util.UUID;

public class TestUUID 
{
	public static void main(String[] args)
	{
		
		String uuid=UUID.randomUUID().toString() ;
		System.out.println(uuid);
		
		String formatStr=Introspector.decapitalize("OuterClazz.InnerClazz");//outerClazz.InnerClazz
		System.out.println(formatStr);
	}
}
