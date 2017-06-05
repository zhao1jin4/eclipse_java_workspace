package mypackage.test;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;
import java.util.Set;

public class TestMain
{
	public void newLine()
	{
	    
		int r='\r';
		int n='\n';
		System.out.println("ASCII_R:"+r);  //13
		System.out.println("ASCII_N:"+n);  //10
		
		String newLine=System.getProperty("line.separator");
		int len=newLine.length();
		System.out.println("-------------new line length:"+len);
		for(int i=0;i<len;i++)
		{
			int ascii=newLine.charAt(i);
			System.out.println("system new line ASCII_"+i+"___"+ascii);  //windows is   13  10     即\r\n
			
		}
		
		//test   read come from a config file   
		String configLine="\\r\\n";  //here read come from a config file
		String line="";
		if(configLine.length()%2==0)
		{
			if(configLine.startsWith("\\n"))  //can not parse real new line ?????????????
				line="\n";
			else if(configLine.startsWith("\\r\\n"))
				line="\r\n";
//			if(configLine.charAt(0)=='\\' && configLine.charAt(1)=='\r')
//				line="\r";
		}
		
		System.out.printf("testNewLine_" + line +"++++");
	}
	public static void printSystemProperties()
	{
		Properties props=System.getProperties();
		Set<Entry<Object,Object>> entry =props.entrySet();
		for (Iterator<Entry<Object,Object>> iterator = entry.iterator(); iterator.hasNext();)
		{
			Entry each =iterator.next();
			System.out.println(each.getKey()+"="+each.getValue());
		}
	}
	
	public static void testFormat()
	{
		try
		{
			OutputStreamWriter ww=new OutputStreamWriter(new FileOutputStream("C:/temp/test.txt"), Charset.forName("UTF-8"));
			PrintWriter writer= new PrintWriter(ww);
			writer.printf("char\t|__%-10s|\n","abc");
			writer.printf("number__%10d",123);
			writer.close();
			ww.close();
			

			System.out.printf("char\t|__%-10s|\n","abc"); //left align 
			System.out.printf("number__%10d",123);		//right align
			System.out.printf("number__%10.2f",123.12);		//right align
			
			NumberFormat format= NumberFormat.getIntegerInstance();
			String str=format.format(1234567);//1,234,567
			System.out.println(str);

			
			System.out.println(20.0/30.0);
			NumberFormat format1=NumberFormat.getInstance();
			format1.setMaximumFractionDigits(2);
			String val=format1.format((double)20/(double)(20+10)*100);
			System.out.println(val+"%");
			
			
			long x=999999999;
			String s = String.format("%2d,%21d,%3d,%10s", 20,23,23,"aabb");
			System.out.println(s);
			
			s=String.format("R%05d",23);
			System.out.println(s);
			
			 Calendar calendar =   Calendar.getInstance();
			  String today = String.format("today: %1$tY-%1$tm-%1$td", calendar);// %1$是后参数的第一个 
			  today = String.format("today: %1$tY-%<tm-%<td", calendar);// %< 表示使用和前一个相同,即%1$
			   
			  
			  
				
				String res="Ad李sd".toUpperCase();
				System.out.println(res);
				
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		

		{
			double msisdn=13988889999d;
			NumberFormat format=NumberFormat.getInstance();
			format.setMaximumFractionDigits(0);
			String str_msisdn=format.format(msisdn);
			System.out.println(str_msisdn.replace(",", ""));
		}
	
	}
	 // 范型方法:,在方法中声明范型,方法前加<T>
    public static <T> void fromArrayToCollection(T[] a, Collection<T> c) 
    {
        for (T o : a)
            c.add(o);
    }
    static String res;
    public static String testReturn ()
    {
    	return res = "123";
    }
    public static void showThreadStatus()
    {

        Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
//      maps.keySet();
        Set<Map.Entry<Thread, StackTraceElement[]>> set=maps.entrySet();
//      set.iterator();
        for(Map.Entry<Thread, StackTraceElement[]> entry: set)
        {
            Thread thread=entry.getKey();
            System.out.println("Thread id:"+thread.getId()+",name:"+thread.getName()+",status:"+thread.getState());
            for(StackTraceElement ele: entry.getValue())
                System.out.println("\t"+ele);
        }
    }
	public static void main(String[] args) throws Exception
	{
	   
	    showThreadStatus();
	    
		testFormat();
		Date t=Calendar.getInstance().getTime();
		
		System.out.println("Timstatmp:"+t.getTime());
		System.out.println("===arg+=="+args);
		printSystemProperties();
		
		byte[] buf = {0,1,3};//数组的写法
		byte[] buf1 = new byte[]{0,1,3};//数组的写法
		byte[] buf2 = new byte[3];//数组的写法
		
		System.out.println(testReturn());
		System.out.println(res);
		
		Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(64));
	}
	public static Pair getMinMax()//static
	{
		return new Pair();
	}

	//内部类中不能定义静态成员
	//编译器会自动给内部类加上一个reference，指向产生它的那个外部类的对象，如果不想要或者说不需要这个reference，那么我们就可以把这个内部类声明为static，
	//就可以在static方法中 不用加外部类的对象,而直接new
	public static class Pair { //static
		public Pair() {
		}
	}
	
}
