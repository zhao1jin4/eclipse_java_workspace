package quiz.sum.equal;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
/**
 * 如何在一个数组中求出任意几个数的 和 等于给定数
 */
public class Organize1 {
    static int[] iArray = {0, 2, 3, 1, 4, 10, 23, 7, 8, 9, 6, 3};
    static ArrayList<String> list = new ArrayList<String>();
    static Set<String> index = new TreeSet<String>();
    static StringBuilder str;
    static StringBuilder indexStr;
    static int sum;
     
    public static void org(int[] iArray, int start){
        for(int i=0; i<iArray.length; i++){
            sum = iArray[i];
            str = new StringBuilder();
            str.append(iArray[i]);
            indexStr = new StringBuilder();
            indexStr.append(i);
 
            for(int j=start; j<iArray.length; j++){                                   
                if(i != j){
                    sum += iArray[j];
                     
                    if(sum == 10){
                        str.append("+"+ iArray[j]);
                        indexStr.append("+"+ j);
                        int size = index.size();
                        index.add(indexStr.toString());
                         
                        if(index.size() > size)
                            list.add(str.toString());                           
                    }
                    if(sum < 10){
                        str.append("+"+ iArray[j]);
                        indexStr.append("+"+ j);
                    }
                    if(sum > 10){                       
                        sum -= iArray[j];                           
                    }
                }
            }
        }
    }
     
    public static void main(String args[]){   
        for (int i=0; i<iArray.length; i++)
            org(iArray, i);
        System.out.println(list.toString());
    }
}