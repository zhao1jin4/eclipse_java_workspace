package mypackage.test;

public class TestPrimitiveType {
	
	public static void main(String[] args) {
		 int intp1=2000;
		 Integer intc2=2000;
		 Integer intc1=Integer.valueOf(2000);
		 Integer intNo=new Integer(2000);//��ʱ�÷�
		 System.out.println(intp1==intc1);
		 System.out.println(intp1==intNo);
		 System.out.println(intp1==intc2);
		 
		 
        Integer a = 1;
        Integer b = 1; //<=127
        Integer c = 200;  //>127
        Integer d = 200;
        System.out.println(a == b);//true������ͬһ������ cache �Ķ�����ͬһ���ڴ��ַ
        System.out.println(c == d);//false������ new Integer(int)�������µĵ�ַ
        
	}
	
}
