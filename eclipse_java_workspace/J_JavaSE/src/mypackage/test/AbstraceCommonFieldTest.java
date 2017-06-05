package mypackage.test;
abstract class MyAbstract
{
	public static int totalNum=0;
}
class ClassA extends MyAbstract
{
}
  class ClassB extends MyAbstract
{
}
  
  
public class AbstraceCommonFieldTest 
{
	public static void main(String[] args)
	{
		ClassB.totalNum++;
		ClassB.totalNum++;
		ClassB.totalNum++;
		ClassB.totalNum++;
		System.out.println(ClassA.totalNum);
	}
}
