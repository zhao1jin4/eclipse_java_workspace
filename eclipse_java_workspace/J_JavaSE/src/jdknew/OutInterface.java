package jdknew;

public interface OutInterface {  
	public void outMethod();
	
	 public  interface   InInterface { //这个interface 本身就是static的,使用就相录于加了层包名的感觉
		public void inMethod();
	}
	 public class InClass
	 {
		 public static final String CHAR_SET="GBK";
	 }
}

interface  ChildInInterface extends  OutInterface.InInterface
{
	 public void childIn();
}


