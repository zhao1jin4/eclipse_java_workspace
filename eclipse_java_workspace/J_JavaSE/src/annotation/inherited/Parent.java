package annotation.inherited;


@InheritedTest("Parent in class")
public class Parent 
{
	@InheritedTest("Parent in doSomething ") //@���ڸ���ķ�����Ҳ�ǿ��Ե�(��������า�Ǿ�û����),�ĵ���˵����������,��������Ч
	public void doSomething()
	{
		System.out.println("hello in Parent");
	}
}
