package quiz.sum.equal;
/**
 * �����һ��������������⼸������ �� ���ڸ�����
 */
public class SubArrSum {
     
    int[] a=new int[]{0, 2, 3, 1, 4, 10, 23, 7, 8, 9, 6, 3};
    int k=0;
   
    //�г��������в����
    public void sum_M(int[] a,int M){
         
        int max=1<<a.length;
        System.out.println(max);
         
        for(int i=1;i<max-1;i++){
             
            int s=0;
            int k=a.length-1;
            int t=i;
             
            while(t>0){
                if((t&1)>0){
                    s=s+a[k];
                }
                k--;
                t=t>>1;
            }
             
            if(s==M){
                showResult(a,i,M);
            }
        }
    }
     
    private void showResult(int[] a, int i, int m) {
        int k=a.length-1;
        int t=i;
        while(t>0){
            if((t&1)>0){
                System.out.print("  "+a[k]);
            }
            k--;
            t=t>>1;
        }
        System.out.print("  = "+m);
        System.out.println();
    }
 
    public static void main(String[] args) {
        SubArrSum te=new SubArrSum();
        int M=10;
        te.sum_M(te.a, M);
    }
 
}