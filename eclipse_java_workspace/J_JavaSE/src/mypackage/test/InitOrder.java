package mypackage.test;

public class InitOrder
{
	{ //和构造器同一级别，执行顺序看谁在前面
		System.out.println("InitOrder Block");
	}
	public InitOrder ()
	{
		System.out.println("InitOrder");
		
	}

	static {
		System.out.println("InitOrder static");
	}
	
	public static void main(String[] args) {
		new InitOrder();
		new ChildOrder(); 
	 //eclipse运行时选择 InitOrder
	}

	
}

class Other  extends InitOrder
{
	{
		System.out.println("other Block");
	}
	public Other ()
	{
		System.out.println("other");
	}
	static {
		System.out.println("other static");
	}
	
}

class ChildOrder extends InitOrder
{
	{ 
		System.out.println("ChildOrder Block");
	}
	public ChildOrder ()
	{
		System.out.println("ChildOrder");
	}
	
	static {
		System.out.println("ChildOrder static");
	}
	
}