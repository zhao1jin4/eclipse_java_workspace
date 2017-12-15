package quiz;

import java.text.NumberFormat;

public class SquarRoot {

	public static float  getSquarRoot(int num,float preci)throws Exception
	{
		if(num<0)//负数没有平方根
			throw  new Exception("negative number");
		else if (num ==0 || num==1)//0和1单独处理
			return num;
		
		int max=0;
		for (int i=0;i<num;i++)
		{
			max=i; 
			if(i*i> num)
				break;
		}
		
		if( (max-1) * (max-1) == num)
			return max-1;
		
		float p=0.0f;
		for(float j=max-1;j<=max+1;j+=preci)
		{
			p=j;
			if(j*j>num)
				break;
		}
		return p-preci;
	}
	public static void main(String[] args) throws Exception 
	{
		float a=getSquarRoot(2,0.0001f);//
		 
		//-1.0E-4,是-0.0001
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		 
		System.out.println(nf.format(a));
	}

}
