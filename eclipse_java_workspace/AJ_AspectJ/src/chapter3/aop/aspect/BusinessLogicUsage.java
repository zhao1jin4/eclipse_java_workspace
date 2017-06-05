package chapter3.aop.aspect;

public class BusinessLogicUsage
{
	public static void main(String[] args)
	{
		BusinessLogic business = new BusinessLogicCoreConcern();
		business.businessMethod2();
		System.out.println();
	
		business.businessMethod1();
	}
}
