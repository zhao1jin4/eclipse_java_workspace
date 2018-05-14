package mypackage.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


public class ForFunction
{

    public static void main(String[] args)
    {
        final List<String> successKeys =new ArrayList<String>();
        for (int i=0;i<10;i++)
        {
            successKeys.add("数据"+i);
        }
        
        
        
        
        
        TransactionTemplate   transactionTemplate=new TransactionTemplate();
        
        for(final String str: successKeys) //这里 加 final 并会报错,如用for (final int i=0;i<len;i++)就不行了,不能加 
        {
            transactionTemplate.execute(new TransactionCallbackWithoutResult()
            {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status)
                {
                    System.out.println(str);                    
                }
            }); 
        }
        
        //-----方式2
        
        int len=successKeys.size();
        for( int i=0;i<len;i++)
        {   final int j=i;  //多一个变量做final
            transactionTemplate.execute(new TransactionCallbackWithoutResult()
            {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status)
                {
                    System.out.println(successKeys.get(j));                    
                }
            }); 
        }
        

    }

}
