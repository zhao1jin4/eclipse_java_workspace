package quiz;


public class TestCow
{
	public static int  f1=1;
	public static int  f2=1;
	public static void main(String[] args)
	{

		System.out.println("============"+process(11));
		 rabit();
	}
	public static void rabit()
	{
		//潭浩强  Fibonacci 数学问题 兔子 三个月生
		//如果一对兔子每月能生一对小兔（一雄一雌），而每对小兔在它出生后的第三个月里，又能开始生一对小兔
		long  f1=1,f2=1;
		int n=11;//月数
		for(int i=1;i<=n/2;i++)
		{
			System.out.printf("%12d  %12d\n",f1,f2);
			f1=f1+f2;
			f2=f2+f1;
		}
				
	}
	public static int process(int month)//递归的做法
	{
		
//		if(month<3)
//			return 1;
//		f1=process(month-1)+process(month-2);
//		return f1;
//兔子 三个月生，  这个月等上两个月相加的值		
//-----------------------
		
		if(month<4)
			return 1;
		f1=process(month-1)+process(month-3);
//牛 四个月生，  这个月等于上一个加上3个月的值	
		return f1;
	}
}
