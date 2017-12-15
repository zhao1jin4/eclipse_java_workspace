package spring_config;


public class MyLookupBean 
{
	//spring会重写这个方法,<lookup-method 
	public  AnotherBean newAnotherBean()//protected,public 都可
	{
		return null;
	}
	public void doWithAnotherBean()
	{
		AnotherBean another=newAnotherBean();//调用spring的方法
		System.out.println(another.hashCode());
	}
}
