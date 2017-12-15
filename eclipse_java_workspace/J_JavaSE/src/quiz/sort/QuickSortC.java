package quiz.sort;

/*
 ActiionScript 的相同
 
 */
public class QuickSortC 
{

	static void QuickSort(int data[], int left, int right)
	{
	        int temp = data[left];
	        int p = left;
	        int i = left, j = right;
	
	        while (i <= j)
	        {
	                while (j >= p && data[j] >= temp)//最右边是大的,从小到大排序
	                        j--;
	                if(j >= p)
	                {
	                        data[p] = data[j];
	                        p = j;
	                }
	 
	                while (i <= p && data[i] <= temp)//最左边是小的,从小到大排序
	                        i++;
	                if (i <= p)
	                {
	                        data[p] = data[i];
	                        p = i;
	                }
	        }
	        data[p] = temp;
	        if(p - left > 1)
	                QuickSort(data, left, p-1);
	        if(right - p > 1)
	                QuickSort(data, p+1, right);
	}
	public static void main(String[] args)
	{
		int[] data=new int[]{3,8,2,9,6,4,5,7,1};
		QuickSort(  data, 0, data.length-1);
		
		 for (int i=0;i<data.length;i++)
		  {
			  System.out.println(data[i]+",");
		  }
	}

}
