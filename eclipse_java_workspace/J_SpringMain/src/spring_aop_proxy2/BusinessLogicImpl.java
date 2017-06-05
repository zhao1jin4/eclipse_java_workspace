package spring_aop_proxy2;

public class BusinessLogicImpl implements BusinessLogic
{
	public void bar()
	{
		System.out.println("BusinessLogicImp bar !");

	}

	public void foo(String name)
	{
		System.out.println("BusinessLogicImpl foo !");

	}
}
