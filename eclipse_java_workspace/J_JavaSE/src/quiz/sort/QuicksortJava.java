package quiz.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
 
public class QuicksortJava {
	
	
    public static final Random RND = new Random();
 
    private static void swap(Object[] array, int i, int j) {
    	if(i==j)
    		return ;
        Object tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
 
    private static <E> int partition(E[] array, int begin, int end, Comparator<? super E> cmp) {
        int index = begin + RND.nextInt(end - begin + 1);
        E pivot = array[index];
       swap(array, index, end);        
        for (int i = index = begin; i < end; ++ i) {
            if (cmp.compare(array[i], pivot) < 0) {
                swap(array, index++, i);
            }
        }
        swap(array, index, end);        
        return (index);
    }
 
    private static <E> void qsort(E[] array, int begin, int end, Comparator<? super E> cmp) {
        if (end > begin) {
            int index = partition(array, begin, end, cmp);
            qsort(array, begin, index - 1, cmp);
            qsort(array, index + 1,  end,  cmp);
        }
    }
 
    public static <E> void sort(E[] array, Comparator<? super E> cmp)
    {
        qsort(array, 0, array.length - 1, cmp);
    }
    
    
    public static void main(String[] args) 
	{
	//Comparator 和 Comparable不同的
    //最坏时间复杂度 O(n*n), 平均时间复杂度  O(nlogn)
    //辅助空间  O(logn)
	  Comparator<Integer> c1=new Comparator<Integer>(){
			public int compare(Integer o1, Integer o2) 
			{
				int result=   (o1.intValue()==o2.intValue() )?0:( o1.intValue()>o2.intValue() )?1:-1;
				return result;
			}};
			Integer[] data1=new Integer[]{3,8,2,9,6,4,5,7,1};
	  sort(data1,c1);
	  for (int i=0;i<data1.length;i++)
	  {
		  System.out.println(data1[i]+",");
	  }
	 
		 
	}
}