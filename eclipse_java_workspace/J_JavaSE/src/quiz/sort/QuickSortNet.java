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
	            //根据中间键检测前后数的大小，然后互换；i != j的加入是为了在else情况下的处理  
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
	    //谢谢leinad2009帮忙指出这里的错误覆盖  
	    //      ar[istart] = ar[i];  
	    //      ar[i] = itemp;  
	            //递归   
	    //把中间值归入首部分的排序  
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
