package jdknew;

public interface OutInterface {  
	public void outMethod();
	
	 public  interface   InInterface { //���interface �������static��,ʹ�þ���¼�ڼ��˲�����ĸо�
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


