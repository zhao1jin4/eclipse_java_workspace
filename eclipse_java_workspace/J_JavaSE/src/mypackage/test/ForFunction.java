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
            successKeys.add("����"+i);
        }
        
        
        
        
        
        TransactionTemplate   transactionTemplate=new TransactionTemplate();
        
        for(final String str: successKeys) //���� �� final ���ᱨ��,����for (final int i=0;i<len;i++)�Ͳ�����,���ܼ� 
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
        
        //-----��ʽ2
        
        int len=successKeys.size();
        for( int i=0;i<len;i++)
        {   final int j=i;  //��һ��������final
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
