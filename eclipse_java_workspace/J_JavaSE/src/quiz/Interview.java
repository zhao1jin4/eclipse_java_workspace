package quiz;

import java.math.BigDecimal;

public class Interview {

	public static void main(String[] args) 
	{
		  int x=4;
		  System.out.println("value is " +((x>4)?99.9:9));
		  //  ��: 9.0   : ǰ�����double,���Ժ���תΪdouble
		     
		     
		     //const��java�Ĺؼ���,����javaû��ʵ���� 
		     
		     short s1 = 1;
		     //s1 = s1 + 1;//����,Ҫint->short
		     s1 += 1;//
		     
		     //Ч����ߵķ���,ʵ�ִ�1�ӵ�100.
		     int sun = 0;
		     for(int i = 1,j = 100 ; i <= 50 ; i++,j--){
		    	 sun = sun + i + j;
		     }
		     System.out.println("sun is " + sun );
		     
		     
		     
		     System.out.println(5.0942*1000);//5094.2
		     System.out.println(5.0943*1000);//5094.299999999999
		     System.out.println(5.0944*1000); //5094.400000000001   �������ļ������ʾ��ʽ�й�
		     
			System.out.println(5.00-4.90); //�����0.09999999999999964
			System.out.println(5.00f-4.90f);//�����0.099999905
			float ss = new BigDecimal(5.00).subtract(new BigDecimal(4.90)).floatValue(); //��ȷ���
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

