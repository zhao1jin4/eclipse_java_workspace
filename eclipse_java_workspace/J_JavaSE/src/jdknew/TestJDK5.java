package jdknew;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
public class TestJDK5
{
	javax.persistence.spi.ProviderUtil x;
	public int sum(int...a)//¶¯Ì¬²ÎÊý
	{
		int x=a.length;
		return a[0]+a[1];
	}
	public static void main(String[] args)
	{
		TestJDK5 x=new TestJDK5();
		int res=x.sum(12,12);
		System.out.println(res);
	}
}
