package jdk9new;

import java.lang.invoke.MethodHandles;
 
import java.lang.invoke.VarHandle;

public class JDK9NewVar {
	public static void main(String[] args) {
		//sun.misc.Unsafe的替代方案
		String[] sa =new String[10] ;
		 VarHandle avh = MethodHandles.arrayElementVarHandle(String[].class);
		 boolean r = avh.compareAndSet(sa, 9, "expected", "new");
		 System.out.println(r);
		 
		 
		 System.out.println(Bar.VH_FOO_FIELD_I);


	}
}

class Foo {
    int i;
}
class Bar
{
	static final VarHandle VH_FOO_FIELD_I;
	static 
	{
	    try {
	        VH_FOO_FIELD_I = MethodHandles.lookup().
	            in(Foo.class).
	            findVarHandle(Foo.class, "i", int.class);
	        
	    } catch (Exception e) {
	        throw new Error(e);
	    }
	}
}
 