package spring_aop_proxy;



public class ComputerImpl  implements Computer,Monitor
{
	public void mouseInfo()
	{
		System.out.println("this is a mouse info");
	}
	public void computerInfo()
	{

		System.out.println("this is a computer info");
	}
	public void showMessage() {
		System.out.println("this is a show Message info");
	}
	public void display()
	{
		System.out.println("Monotor Dispaly implements class Invoked");
		
	}

}
