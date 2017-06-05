package spring_db_transaction;

import org.springframework.transaction.annotation.Transactional;

//@Transactional //如@Transactional写在接口上,必须使用proxy-target-class="false" JDK代理
public interface StudentDao  
{
	public void updateStudentAge() throws Exception;
	public Student getStudent();
}
