package jdknew;

public class JDK5DynamicParam
{
	public int sum(int...a)//¶¯Ì¬²ÎÊý
	{
		int x=a.length;
		return a[0]+a[1];
	}
	public static void main(String[] args)
	{
		JDK5DynamicParam x=new JDK5DynamicParam();
		int res=x.sum(12,12);
		System.out.println(res);
	}
}
