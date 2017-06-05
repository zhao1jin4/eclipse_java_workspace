package quiz.sort;
public class QuickSortNet {
	  private static int[] QuickSort(int[] ar,int istart,int iend)
	  {  
	        if(istart < iend)  
	        {  
	            int i = istart - 1;  
	            int j = iend + 1;  
	            int itemp = ar[istart];  
	            int t;  
	            //�����м�����ǰ�����Ĵ�С��Ȼ�󻥻���i != j�ļ�����Ϊ����else����µĴ���  
	            while(i + 1 != j && i != j)
	            {                  
	                if(ar[i+1] <itemp){  
	                    i++;  
	                }  
	                else if(ar[j-1] > itemp)
	                {  
	                    j--;  
	                }  
	                else
	                {  
	                    t = ar[i+1];  
	                    ar[++i] =ar[j-1];  
	                    ar[--j] = t;  
	                }                     
	            }  
	    //ллleinad2009��æָ������Ĵ��󸲸�  
	    //      ar[istart] = ar[i];  
	    //      ar[i] = itemp;  
	            //�ݹ�   
	    //���м�ֵ�����ײ��ֵ�����  
	    //      QuickSort(ar,istart,i-1);  
	            QuickSort(ar,istart,i);  
	            QuickSort(ar,i+1,iend);                   
	        }  
	        return ar;                
	}  
	public static void main(String[] args) 
	{
	  int[] data=new int[]{3,8,2,9,6,4,5,7,1};
	  int[] result= QuickSort(data,0,data.length-1);
	  
	  
	  for (int i=0;i<data.length;i++)
	  {
		  System.out.println(data[i]+",");
	  }
		 
	}

}
