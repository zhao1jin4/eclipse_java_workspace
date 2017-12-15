package reflect;

import java.lang.reflect.Field;

public class ClassPrivate 
{
	
	private String name;
	private int age;
	private String[] address;
	
	//javap -s -private 
	public static void printPrivateFieldName()
	{
		Field[] fields=ClassPrivate.class.getDeclaredFields();
		for (Field field  : fields)
		{
			System.out.println(field.toString());
		}
		
	}

	public static void main(String[] args) {
		printPrivateFieldName();


		
		try{
			ClassPrivate x=ClassPrivate.class.newInstance();
			x.printPrivateFieldName();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
