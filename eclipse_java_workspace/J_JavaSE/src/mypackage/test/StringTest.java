package mypackage.test;

public class StringTest {

	public static void main(String[] args) {
		
		String a="hello";
		String b="he"+new String("llo");
		String c="he"+ "llo" ;//���string pool
		String x="llo";
		String e="he"+ x ;//�ͱ�����ӻ���׵������
		System.out.println(a==b);//false
		System.out.println(a==c);//true
		System.out.println(a==e);//false
		
		
		

	}

}
