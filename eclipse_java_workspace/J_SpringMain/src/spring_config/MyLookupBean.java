package spring_config;


public class MyLookupBean 
{
	//spring����д�������,<lookup-method 
	public  AnotherBean newAnotherBean()//protected,public ����
	{
		return null;
	}
	public void doWithAnotherBean()
	{
		AnotherBean another=newAnotherBean();//����spring�ķ���
		System.out.println(another.hashCode());
	}
}
