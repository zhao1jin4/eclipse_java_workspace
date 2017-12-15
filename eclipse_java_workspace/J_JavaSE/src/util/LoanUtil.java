package util;


public class LoanUtil
{
    /**
     * �ȶϢ
     * @param totalPrincipal �ܻ���ȫ������
     * @param periods ��������
     * @param interestRate ��������
     * @return ÿ�ڻ����
     */
    public static double calcTotalEqual(double totalPrincipal, int periods,  double interestRate)  
    {
        if(periods <=0 || totalPrincipal <=0 )
            return 0;
        
        if (interestRate == 0)    
            return totalPrincipal/periods;
         else if(interestRate <0)
            return -1;
        
        double baseRate = 1 + interestRate;
//        double b0 = PowerCal(baseRate, periods);
        double b=Math.pow(baseRate, periods);
        
        double amt = totalPrincipal * interestRate * b / (b - 1);
        return amt;
    }
    
    
    /**
     * 
     * �� �ƣ�PowerCal �������������㸡������������ 
     * ����˵����
     *  base������
     *  exp��ָ����ֻ�ܴ��ڵ���0 
     *  �� �� �룺��������expС�ڵ���0ʱ������1  
     * 
     *
    private static double PowerCal(double base, long exp) {
        double result = 1;
        long i;

        for (i = 1; i <= exp; i++) {
            result *= base;

        }
        return result;
    }
    */
    
    public static void main(String[] args) 
    {
        double interestYearRate=0.049;// 4.9%
        double interestMonthRate=interestYearRate/12;
        
        double totalLoanAmount=500000;//50 w
        int periodMonth=13*12;//13��
        
        double eachMounthReturn= calcTotalEqual(totalLoanAmount,periodMonth,interestMonthRate);
       System.out.println("�ȶϢÿ�»����"+eachMounthReturn); //4339.937160
       
        
       
    }
}
