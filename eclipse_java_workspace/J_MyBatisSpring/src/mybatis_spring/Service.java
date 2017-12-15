package mybatis_spring;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@org.springframework.stereotype.Service("servcie")
public class Service {

    @Resource(name="batchSqlSession")
    private  SqlSession    batchSqlSession ;
    
    @Resource(name="sqlSession")
    private  SqlSession    sqlSession ;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
   
    @Autowired
    private Dao dao;
    
    @Autowired
    private AnnoationDao annoationDao;
  
	  public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public AnnoationDao getAnnoationDao() {
		return annoationDao;
	}

	public void setAnnoationDao(AnnoationDao annoationDao) {
		this.annoationDao = annoationDao;
	}
 
    public TransactionTemplate getTransactionTemplate()
    {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate)
    {
        this.transactionTemplate = transactionTemplate;
    }

    //spring的@Transactional不是javax的
    @Transactional(propagation = Propagation.REQUIRED ,isolation=Isolation.READ_COMMITTED,timeout=10
            //,rollbackFor=IOException.class,rollbackForClassName="FileNotFoundException",noRollbackFor=IOException.class
            )
    public void doInTransactionUseAnnotation()
	{
	    dao.addAge(101);
	   // sqlSession.commit();  //使用Spring声明事务不允许手工commit
	    if(1+1==2)
            throw new RuntimeException ("出现事件中断错误");
        dao.plusAge(102);
      
	}
    
    
    @Transactional(propagation = Propagation.REQUIRED ,isolation=Isolation.READ_COMMITTED,timeout=10)
	 public void doInTransactionUseBatchSesssion()
	 {
        //如是 Batch 类型的session做事务没用的???   正常session是可以的
        
//	     SqlSession batchSqlSession = sqlSession.getSqlSessionFactory().openSession(
//	             ExecutorType.BATCH, false);
	    
	    
	     for(int i=0;i<10;i++)
	     {
	         Employee emp = new Employee();
	         emp.setAge(800+i);
	         emp.setId(8800+i);
	         emp.setUsername("batchUser"+i);
	         batchSqlSession.insert("EmployeeMapper.insert",emp);
	         
	         if(i++%5==0)
	             sqlSession.clearCache();
	     }
	 }
	public void doInTransactionUseTemplate()
	  {
	
//			transactionTemplate.execute(new TransactionCallback<Integer>() {
//				@Override
//				public Integer doInTransaction(TransactionStatus status) {
//					dao.addAge(101);
//					dao.plusAge(102);
//					return 0;
//				}
//			});
//			
		
		//------------
	    try{
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult( TransactionStatus status) {
					dao.addAge(101);
					if(1-1==0)
					{
//					    Object firstSavePoint= status.createSavepoint();
//					    if(status.hasSavepoint())
//					        status.rollbackToSavepoint(firstSavePoint);
//					    status.releaseSavepoint(firstSavePoint);
//					    status.setRollbackOnly();	//让事务回滚				        
					}
					if(1+1==2)
					    throw new RuntimeException ("出现事件中断错误");
					dao.plusAge(102);
				}
			});
	    }catch(Exception ex)
	    {
	        System.out.println("事务失败,原因:"+ex.getMessage());
	    }
	  }
	
}

