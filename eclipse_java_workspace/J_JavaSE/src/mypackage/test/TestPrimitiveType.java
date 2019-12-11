package mypackage.test;

public class TestPrimitiveType {
	
	public static void main(String[] args) {
		 int intp1=2000;
		 Integer intc2=2000;
		 Integer intc1=Integer.valueOf(2000);
		 Integer intNo=new Integer(2000);//过时用法
		 System.out.println(intp1==intc1);
		 System.out.println(intp1==intNo);
		 System.out.println(intp1==intc2);
		 
		 
        Integer a = 1;
        Integer b = 1; //<=127
        Integer c = 200;  //>127
        Integer d = 200;
        System.out.println(a == b);//true，对于同一个数字 cache 的对象是同一块内存地址
        System.out.println(c == d);//false，重新 new Integer(int)，开辟新的地址
        
	}
	
}
