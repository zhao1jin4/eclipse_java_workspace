package serial_javassist;

public class StringPerformance {
	public   String appendStr(int len)
	{
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<len;i++)
		{
			builder.append(i+'a');
			
		}
		return builder.toString();
	}
	 
	
	public   String plusStr(int len)
	{
		String str="";
		for(int i=0;i<len;i++)
		{
			str+=(i+'a');
			
		}
		return str;
	}
}
