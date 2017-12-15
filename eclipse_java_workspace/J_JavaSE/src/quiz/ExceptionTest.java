package quiz;

import java.util.TreeMap;


//private  class ABC
//{
//	
//}
public class ExceptionTest {

	public void haveError()
	{
		 
		throw new OutOfMemoryError(); //应用不应该(但也可以)抛Error ,通常由JVM 抛
	}
	public void test()
	{
	    StringBuffer errorMsg=new StringBuffer();
		try
		{
			 haveError();
		}catch(OutOfMemoryError err) 
		{
			System.err.println("有错误");
			//err.printStackTrace();

			//效果同printStackTrace
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
