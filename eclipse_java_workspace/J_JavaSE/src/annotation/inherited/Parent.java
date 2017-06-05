package annotation.inherited;


@InheritedTest("Parent in class")
public class Parent 
{
	@InheritedTest("Parent in doSomething ") //@放在父类的方法上也是可以的(如果被子类覆盖就没用了),文档上说除了在类上,其它都无效
	public void doSomething()
	{
		System.out.println("hello in Parent");
	}
}
