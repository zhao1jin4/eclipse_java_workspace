package spring_junit;

import org.springframework.util.Assert;
 

public class TestAssert {
	public static void main(String[] args) {
		Object obj=null;
		Assert.notNull(obj,"error,obj is null");
		
		System.out.println("program end");
	}
}
