package spring_db_transaction;

import org.springframework.transaction.annotation.Transactional;

//@Transactional //��@Transactionalд�ڽӿ���,����ʹ��proxy-target-class="false" JDK����
public interface StudentDao  
{
	public void updateStudentAge() throws Exception;
	public Student getStudent();
}
