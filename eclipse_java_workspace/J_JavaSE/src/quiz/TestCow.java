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
		//̶��ǿ  Fibonacci ��ѧ���� ���� ��������
		//���һ������ÿ������һ��С�ã�һ��һ�ƣ�����ÿ��С������������ĵ�����������ܿ�ʼ��һ��С��
		long  f1=1,f2=1;
		int n=11;//����
		for(int i=1;i<=n/2;i++)
		{
			System.out.printf("%12d  %12d\n",f1,f2);
			f1=f1+f2;
			f2=f2+f1;
		}
				
	}
	public static int process(int month)//�ݹ������
	{
		
//		if(month<3)
//			return 1;
//		f1=process(month-1)+process(month-2);
//		return f1;
//���� ����������  ����µ�����������ӵ�ֵ		
//-----------------------
		
		if(month<4)
			return 1;
		f1=process(month-1)+process(month-3);
//ţ �ĸ�������  ����µ�����һ������3���µ�ֵ	
		return f1;
	}
}
