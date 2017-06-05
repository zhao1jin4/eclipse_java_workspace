package quiz.sort;

import java.util.Stack;

public class QuickSortNoneCursive {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        QuickSortNoneCursive t=new QuickSortNoneCursive();  
        t.test();  
    }  
    public void test(){  
        int a[]={10,1,4,7,8,6,3,4,4,4,4,4,2,5,9,4,2};  
        //int a[]={};  
        //printArray(a);  
        //quickSort(a,0,a.length-1);  
        nonRecrutSort(a);  
        //nonRecrutQuickSort(a);  
        printArray(a);  
        //partition(a, 0, 5);  
    }  
    public void quickSort(int[] a,int start,int end){//�ݹ����  
        if(start<end){  
            quickSort(a,start,partition(a,start,end)-1);  
            quickSort(a,partition(a,start,end)+1,end);  
        }  
    }  
    public void nonRecrutSort(int[] a){//�ǵݹ���ţ�����ջ  
        //��������ջ��һ�����ڱ���  
        if(a==null||a.length<0) return;  
        Stack<Integer> startStack=new Stack<Integer>();//���浱ǰ���ֵ����λ  
        Stack<Integer> endStack=new Stack<Integer>();//���浱ǰ���ֵ����λ  
        int start=0;  
        int end=a.length-1;  
              
        int pivotPos;  
          
        startStack.push(start);  
        endStack.push(end);  
          
        while(!startStack.isEmpty()){  
            start=startStack.pop();  
            end=endStack.pop();  
            pivotPos=partition(a, start, end);  
            if(start<pivotPos-1){  
                startStack.push(start);  
                endStack.push(pivotPos-1);  
            }  
            if(end>pivotPos+1){  
                startStack.push(pivotPos+1);  
                endStack.push(end);  
            }  
        }  
    }  
  
    public void nonRecrutQuickSort(int a[]){  
        if(a==null||a.length<=0)return;  
        Stack<Integer> index=new Stack<Integer>();  
        int start=0;  
        int end=a.length-1;  
        int pivotPos;  
              
        index.push(start);  
        index.push(end);  
              
        while(!index.isEmpty()){  
            end=index.pop();  
            start=index.pop();  
              
            pivotPos=partition(a,start,end);  
            if(start<pivotPos-1){  
                index.push(start);  
                index.push(pivotPos-1);  
            }  
            if(end>pivotPos+1){  
                index.push(pivotPos+1);  
                index.push(end);  
            }  
        }     
    }  
      
    public int partition(int[] a,int start,int end){//�ֿ鷽����������a�У����±��start��end�����н��л���  
        int pivot=a[start];                     //�ѱ�pivot(��ʼ��pivot=a[start]С�����ƶ���pivot�����  
        while(start<end){                       //�ѱ�pivot������ƶ���pivot���ұ�  
            while(start<end&&a[end]>=pivot) end--;  
            a[start]=a[end];  
            a[end]=-1;
            while(start<end&&a[start]<=pivot) start++;  
            a[end]=a[start];
            a[start]=-1;
        }  
        a[start]=pivot;  
        return start;//���ػ��ֺ��pivot��λ��  
        //printArray(a);  
    }  
      
    public void printArray(int a[]){//��ӡ�������ݵķ��������ڲ���  
        for(int i=0;i<a.length;i++){  
            System.out.print(a[i]+" ");  
        }  
        System.out.println();  
    }  
  
}  