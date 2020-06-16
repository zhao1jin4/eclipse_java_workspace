package struct.proxy;



public class MainApp
{
	public static void main(String[] args) throws Exception {
		Daili da=new Daili();
		Computer com=(Computer)da.bind(new ComputerImpl());
		com.buy("50");
		
	}

}
