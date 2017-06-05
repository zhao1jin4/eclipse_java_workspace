package swing_test;
import java.io.*;
class PrintLines 
{
	public static void main(String[] args) throws Exception
	{
		FileInputStream file=new FileInputStream("PrintLines.java");
		InputStreamReader is =new InputStreamReader(file);
		LineNumberReader lineCounter=new LineNumberReader (is);
		String nextLine=null;
		System.out.println("input text and Enter key ,the exit command quit programe");
		try
		{
			//nextLine=lineCounter.readLine();
		//	char[] ch=new char[1000];
			while((nextLine=lineCounter.readLine())!=null)
			{

				if(nextLine.length()==0)

					//lineCounter.setLineNumber(001);
			System.out.print(lineCounter.getLineNumber());
			System.out.print(":");
			System.out.println(nextLine);
			
			}	
			
		}
		catch (Exception e )
		{
			e.printStackTrace();
		}
	}
}
