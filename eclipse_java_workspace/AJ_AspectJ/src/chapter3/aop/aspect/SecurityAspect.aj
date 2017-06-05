package chapter3.aop.aspect;

public aspect SecurityAspect {
	private pointcut securityExcution():  //注意是:
		execution (public void BusinessLogic.businessMethod1());
	before():securityExcution()
	{
		doSecurity();
	}
	public void doSecurity()
	{
		System.out.println("doing security check");
	}
}
