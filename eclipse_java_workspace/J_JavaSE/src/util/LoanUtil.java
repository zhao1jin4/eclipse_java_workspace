package util;


public class LoanUtil
{
    /**
     * 等额本息
     * @param totalPrincipal 总货款额，全部本金
     * @param periods 贷款期限
     * @param interestRate 贷款利率
     * @return 每期还款额
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
     * 名 称：PowerCal 功能描述：计算浮点数的整数幂 
     * 参数说明：
     *  base：底数
     *  exp：指数，只能大于等于0 
     *  返 回 码：计算结果，exp小于等于0时，返回1  
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
        int periodMonth=13*12;//13年
        
        double eachMounthReturn= calcTotalEqual(totalLoanAmount,periodMonth,interestMonthRate);
       System.out.println("等额本息每月还款额"+eachMounthReturn); //4339.937160
       
        
       
    }
}
