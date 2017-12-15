package quiz;
//´òÓ¡ËØÊı
public class PrimeNumber
{
	public static void printPrimeNumber(int begin,int end)
	{
		int n, i, flag;
		for (n = begin; n < end; n++)
		{
			flag = 1;
			for (i = 2; i <= n / 2; i++)
			{
				if (n % i == 0)
				{
					flag = 0;
					break;
				}
			}
				
			if (flag == 1)
				System.out.printf("%d ", n);
		}
	}
	public static void main(String[] args)
	{
		printPrimeNumber(100,200);
	}
}
