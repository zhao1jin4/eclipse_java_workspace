package quiz;

import java.util.TreeMap;


//private  class ABC
//{
//	
//}
public class ExceptionTest {

	public void haveError()
	{
		 
		throw new OutOfMemoryError(); //Ӧ�ò�Ӧ��(��Ҳ����)��Error ,ͨ����JVM ��
	}
	public void test()
	{
	    StringBuffer errorMsg=new StringBuffer();
		try
		{
			 haveError();
		}catch(OutOfMemoryError err) 
		{
			System.err.println("�д���");
			//err.printStackTrace();

			//Ч��ͬprintStackTrace
            errorMsg.append(err.getMessage()).append("\n");
            for(StackTraceElement ele:err.getStackTrace())
            {
                errorMsg.append("|\t").append(ele.toString()).append("\n");
            }
            
		}catch(ArrayIndexOutOfBoundsException ex)
		{
			
		}catch(ClassCastException ex)
		{
			
		}
		
		
	}
	
	public static void main(String[] args) {
		 
		new ExceptionTest().test();
	}

}
