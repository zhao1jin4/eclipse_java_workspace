package chapter3.aop.aspect;

public aspect TransactionAspect
{
	private pointcut transactionExecution():
		execution(public void BusinessLogic.businessMethod2())||
		execution(public void BusinessLogic.businessMethod1());
	//拦截,在方法调用之前做一些事情
	before():transactionExecution()
	{
		start();
	}
	after():transactionExecution()
	{
		end();
	}
	public void start()
	{
		System.out.println("start");
	}
	public void end()
	{
		System.out.println("end");
	}
}
