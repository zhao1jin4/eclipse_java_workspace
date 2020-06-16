package quiz.sum.equal;
public class ArrSum {
 
    /**
     * 如何在一个数组中求出任意几个数的 和 等于给定数
     */
 
    public static void main(String[] args) {
        int[] a = { 0, 2, 3, 1, 4, 10, 23, 7, 8, 9, 6, 3 };
        int initVal = 10;
        System.out.println(  a.length);
        System.out.println(1<< a.length);
        for (int i = 1; i < 1 << a.length; i++) {
            int sum = 0;
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < a.length; j++) {
                if ((i & 1 << j) != 0) { 
                    sum += a[j];
                    sb.append(a[j]).append("+");
                }
            }
            if (sum == initVal) {
                System.out.println(sb);
            }
        }
    }
     
}