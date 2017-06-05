package quiz;

import java.math.BigDecimal;

public class Interview {

	public static void main(String[] args) 
	{
		  int x=4;
		  System.out.println("value is " +((x>4)?99.9:9));
		  //  答案: 9.0   : 前面的是double,所以后面转为double
		     
		     
		     //const是java的关键字,但是java没有实现它 
		     
		     short s1 = 1;
		     //s1 = s1 + 1;//报错,要int->short
		     s1 += 1;//
		     
		     //效率最高的方法,实现从1加到100.
		     int sun = 0;
		     for(int i = 1,j = 100 ; i <= 50 ; i++,j--){
		    	 sun = sun + i + j;
		     }
		     System.out.println("sun is " + sun );
		     
		     
		     
		     System.out.println(5.0942*1000);//5094.2
		     System.out.println(5.0943*1000);//5094.299999999999
		     System.out.println(5.0944*1000); //5094.400000000001   浮点数的计算机表示方式有关
		     
			System.out.println(5.00-4.90); //结果是0.09999999999999964
			System.out.println(5.00f-4.90f);//结果是0.099999905
			float ss = new BigDecimal(5.00).subtract(new BigDecimal(4.90)).floatValue(); //正确结果
			System.out.println(ss);
			
			
			
	}
}


class Test {
    public static void main(String[] args) {
	int x = 4;
	java.util.Date date = (x > 4) ? new A() : new B();
}
}

class A extends java.util.Date {
	A()
	{
		System.out.println("A");
	}
}
class B extends java.util.Date {
	B()
	{
		System.out.println("B");
	}
	
} 

