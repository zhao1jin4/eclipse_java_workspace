package quiz;

public class TestStringPool {

	public static void main(String[] args) {
		//String pool
		String a="test";
		String b="test123";
		
		String stra=new String("test");
		String strb=new String("test123");
		System.out.println(stra.intern()==a);//true
		System.out.println(b == a+"123");//false,因a是变量
		
		
		
		String c="abc"+"123";
		String bb="abc123";
		System.out.println(bb == c);// true
		System.out.println("abc123" == c);//true
		
		System.out.println(new String("abc") == new String("abc")); //false,用new 是在堆区
	}

}
