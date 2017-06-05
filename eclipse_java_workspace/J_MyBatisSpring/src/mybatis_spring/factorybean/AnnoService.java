package mybatis_spring.factorybean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mybatis_spring.Employee;
import mybatis_spring.factorybean.dao.AnnoEmployeeInterface;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service("annoService")
public class AnnoService
{
    
    @Resource(name="annoEmployeeBatchItemWriter")
    private  MyBatisBatchItemWriter<Employee>    annoEmployeeBatchItemWriter ;
    
    @Resource(name="xmlEmployeeBatchItemWriter")
    private  MyBatisBatchItemWriter<Employee>    xmlEmployeeBatchItemWriter ;
    

    @Resource(name="batchSqlSession")
    private  SqlSession    batchSqlSession ;
    
    @Resource(name="sqlSession")
    private  SqlSession    sqlSession ;
    
  
    
    @Autowired
    private TransactionTemplate transactionTemplate;
   
    @Autowired
    AnnoEmployeeInterface annoEmployeeInterface;
    
    
    
    //spring的@Transactional不是javax的
    @Transactional(propagation = Propagation.REQUIRED ,isolation=Isolation.READ_COMMITTED,timeout=10
            //,rollbackFor=IOException.class,rollbackForClassName="FileNotFoundException",noRollbackFor=IOException.class
            )
    public void doInTransactionUseAnnotation()
    {
        annoEmployeeInterface.deleteByEmployeeId(102);
        
        Employee emp = new Employee();
        emp.setAge(33);
        emp.setId(103);
        emp.setUsername("lisi103");
        annoEmployeeInterface.insert(emp);
    }
     
    public void doInTransactionUseTemplate()
      {
        try{
            
          //不能使用MyBatis的事务,即不要在SqlSessionFactoryBean中注入dataSource和transactionFactory,要用Spring的,
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult( TransactionStatus status) {
                    annoEmployeeInterface.deleteByEmployeeId(102);
                    
                    Employee emp = new Employee();
                    emp.setAge(33);
                    emp.setId(103);
                    emp.setUsername("lisi103");
                    annoEmployeeInterface.insert(emp);
                    
                    status.setRollbackOnly();
                }
            });
        }catch(Exception ex)
        {
            System.out.println("事务失败,原因:"+ex.getMessage());
            ex.printStackTrace();
        }
      }
 
    public void batchUseAnnotation()
    {
        List<Employee> all=new ArrayList<Employee>();
        for(int i=0;i<10;i++)
        {
            Employee emp = new Employee();
            emp.setId(500+i);
            emp.setAge(33);
            emp.setUsername("mapperFactoryBatch"+i);
            all.add(emp);
        }
        xmlEmployeeBatchItemWriter.setAssertUpdates(false);
        xmlEmployeeBatchItemWriter.write(all);
        
        /*
        try{
            //如有@Param("_e") 不行 ???
            annoEmployeeBatchItemWriter.write(all);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }*/
        
        
        
    }
}

