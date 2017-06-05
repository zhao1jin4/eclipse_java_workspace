package helloword;

public aspect World
{
	pointcut greeting() : execution(* Hello.sayHello(..));//格式
	after() returning() : greeting() //returning()
	{
		System.out.println(" World!");
	}
}
