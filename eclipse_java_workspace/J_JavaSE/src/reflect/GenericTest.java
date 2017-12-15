package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GenericTest
{   
    public List<String> list = new LinkedList<>();
	
//	public List<Date> list = new LinkedList<>();
	
    public static void main(String[] args) throws SecurityException, NoSuchFieldException
    { 
    	Field field= GenericTest.class.getField("list");
        ParameterizedType pt = (ParameterizedType)field.getGenericType();
        Type type=pt.getActualTypeArguments()[0];
        System.out.println(type.getTypeName());// java.lang.String
    }
}  