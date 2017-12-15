package not_test;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class SpringTransaction {
	public static void main(String[] args) 
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
//		TransactionStatus status = txManager.getTransaction(def);
//		try {
//		userMapper.insertUser(user);
//		}catch (MyException ex) {
//		txManager.rollback(status);
//		throw ex;
//		}
//		txManager.commit(status);
	}

}
